package com.university.student;

import com.university.configuration.PersistenceTestConfiguration;
import com.university.configuration.StudentControllerTestConfiguration;
import com.university.generics.GenericService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {PersistenceTestConfiguration.class, StudentControllerTestConfiguration.class})
@ActiveProfiles("dev")
public class StudentControllerTest {

    private static final String URL = "http://localhost:8080/api/students";
    private MockMvc mockMvc;
    @Autowired
    private GenericService<Student> studentGenericService;
    @Autowired
    private StudentService studentService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(studentGenericService, studentService))
                .build();
    }

    @Test
    public void shouldControllerReturnOkStatusAndJsonBodyWhenFirstNameExists() throws Exception {
        //Given
        String firstName = "Mark";
        List<Student> studentsWithNameMark = studentService.getByFirstName(firstName);
        //Then
        mockMvc.perform(get(URL + "/byFirstName/" + firstName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(studentsWithNameMark.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(studentsWithNameMark.get(0).getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(studentsWithNameMark.get(0).getLastName())))
                .andExpect(jsonPath("$[0].age", is(studentsWithNameMark.get(0).getAge())))
                .andExpect(jsonPath("$[0].email", is(studentsWithNameMark.get(0).getEmail())))
                .andExpect(jsonPath("$[0].studyField", is(studentsWithNameMark.get(0).getStudyField())))
                .andExpect(jsonPath("$[1].id", is(studentsWithNameMark.get((1)).getId().intValue())))
                .andExpect(jsonPath("$[1].firstName", is(studentsWithNameMark.get((1)).getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(studentsWithNameMark.get((1)).getLastName())))
                .andExpect(jsonPath("$[1].age", is(studentsWithNameMark.get((1)).getAge())))
                .andExpect(jsonPath("$[1].email", is(studentsWithNameMark.get((1)).getEmail())))
                .andExpect(jsonPath("$[1].studyField", is(studentsWithNameMark.get((1)).getStudyField())));
    }


    @Test
    public void shouldControllerReturnEmptyBodyWhenFirstNameNotExistsOrContainsWhiteSpaces() throws Exception {
        //Given
        String emptyFirstName = "    ";
        String notExistsFirstName = "fRANK";
        //Then
        mockMvc.perform(get(URL + "/byFirstName/" + emptyFirstName))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(URL + "/byFirstName/" + notExistsFirstName))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get(URL + "/byFirstName/" + null))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldControllerReturnBadRequestWhenFirstNameIsNotDefined() throws Exception {
        mockMvc.perform(get(URL + "/byFirstName/"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    //More tests should be done

}
