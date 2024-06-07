// package com;

// import static org.mockito.Mockito.when;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;

// import com.crud.task.controller.TaskController;
// import com.crud.task.service.TaskService;

// @WebMvcTest(TaskController.class)
// public class TaskControllerTest {

// @Autowired
// private MockMvc mockMvc;

// @MockBean
// private TaskService taskService;

// @Test
// public void testUpdateTaskStatus() throws Exception {
// mockMvc.perform(put("/tasks/1/status/DONE"))
// .andExpect(status().isOk());
// }
// }
