document.addEventListener("DOMContentLoaded", function () {
  const taskForm = document.getElementById("taskForm");
  const taskNameInput = document.getElementById("taskName");
  const taskDescriptionInput = document.getElementById("taskDescription");
  const taskStatusInput = document.getElementById("taskStatus");
  const taskIdInput = document.getElementById("taskId");
  const newStatusInput = document.getElementById("newStatus");
  const taskListsContainer = document.getElementById("taskListsContainer");

  function fetchTasks() {
    fetch("http://localhost:8080/api/retrieveTasks")
      .then((response) => response.json())
      .then((tasks) => {
        taskListsContainer.innerHTML = "";
        const tasksById = {};
        tasks.forEach((task) => {
          if (!tasksById[task.id]) {
            tasksById[task.id] = [];
          }
          tasksById[task.id].push(task);
        });
        Object.keys(tasksById).forEach((taskId, i) => {
          const taskList = document.createElement("ul");
          taskList.className = "task-list";
          tasksById[taskId].forEach((task) => {
            const li = document.createElement("li");
            li.innerHTML = `<strong>Id: ${taskId} <br> Name:</strong> ${task.name}<br><strong>Description:</strong> ${task.description}<br><strong>Status:</strong> ${task.status}`;

            taskList.appendChild(li);
          });
          taskListsContainer.appendChild(taskList);
        });
      });
  }

  function createTask(name, description, status) {
    fetch("http://localhost:8080/api/createNewTask", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name, description, status }),
    }).then(() => fetchTasks());
  }

  function updateTaskStatus(id, status) {
    fetch(`http://localhost:8080/api/updateTask/${id}/${status}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ id, status }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Task with provided ID does not exist");
        }
      })
      .then(() => fetchTasks())
      .catch((error) => {
        console.error("Error updating task status:", error.message);
        alert(error.message);
      });
  }

  taskForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const taskName = taskNameInput.value.trim();
    const taskDescription = taskDescriptionInput.value.trim();
    const taskStatus = taskStatusInput.value.trim();

    if (taskName !== "" && taskDescription !== "" && taskStatus !== "") {
      createTask(taskName, taskDescription, taskStatus);
      taskNameInput.value = "";
      taskDescriptionInput.value = "";
      taskStatusInput.value = "";
    }
  });

  updateForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const taskId = taskIdInput.value.trim();
    const taskStatus = newStatusInput.value.trim();

    if (taskId !== "" && taskStatus !== "") {
      updateTaskStatus(taskId, taskStatus);
      taskIdInput.value = "";
      newStatusInput.value = "";
    }
  });

  fetchTasks();
});
