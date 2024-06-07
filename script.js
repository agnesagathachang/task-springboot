document.addEventListener("DOMContentLoaded", function () {
  const taskForm = document.getElementById("taskForm");
  const taskInput = document.getElementById("taskInput");
  const taskList = document.getElementById("taskList");

  // Function to fetch tasks from the API and display them
  function fetchTasks() {
    fetch("http://localhost:8080/api/retrieveTasks")
      .then((response) => response.json())
      .then((tasks) => {
        taskList.innerHTML = "";
        tasks.forEach((task) => {
          const li = document.createElement("li");
          li.textContent = task.name;
          if (task.completed) {
            li.classList.add("completed");
          }
          li.addEventListener("click", () => {
            updateTaskStatus(task.id, !task.completed);
          });
          taskList.appendChild(li);
        });
      });
  }

  // Function to create a new task via the API
  function createTask(name) {
    fetch("http://localhost:8080/api/createNewTask", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name }),
    }).then(() => fetchTasks());
  }

  // Function to update the status of a task via the API
  function updateTaskStatus(id, completed) {
    fetch(`http://localhost:8080/api/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ completed }),
    }).then(() => fetchTasks());
  }

  // Event listener for form submission
  taskForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const taskName = taskInput.value.trim();
    if (taskName !== "") {
      createTask(taskName);
      taskInput.value = "";
    }
  });

  // Initial fetch of tasks when the page loads
  fetchTasks();
});
