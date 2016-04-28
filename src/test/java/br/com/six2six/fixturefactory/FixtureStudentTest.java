package br.com.six2six.fixturefactory;

import br.com.six2six.bfgex.Gender;
import br.com.six2six.fixturefactory.function.DependentFunction;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.model.Student;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class FixtureStudentTest {

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.six2six.template");
    }

    @Test
    public void fixtureAnyStudent() {
        Student student = Fixture.from(Student.class).gimme("valid");
        assertNotNull("Student should not be null", student);
        assertNotNull("Students id should not be null", student.getId());
        assertTrue("Tests taken should be greather than 0", student.getTestsTaken() > 0);
        assertTrue("Best score should be greather than 0", student.getTestsTaken() > 0);
    }

    @Test
    public void fixtureFemaleStudent() {
        Student student = Fixture.from(Student.class).gimme("validFemaleStudent");
        assertNotNull("Female Student should not be null", student);
        assertNotNull("Students id should not be null", student.getId());
    }

    @Test
    public void fixtureSharedSequence() {
        Student oneStudent = Fixture.from(Student.class).gimme("sharedSequence");
        Student otherStudent = Fixture.from(Student.class).gimme("otherSharedSequence");
        assertTrue(oneStudent.getId() < otherStudent.getId());
    }

    @Test
    public void fixtureDefaultNumberSequence() {
        Student firstStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
        Student secoundStudent = Fixture.from(Student.class).gimme("defaultNumberSequence");
        assertTrue(firstStudent.getId() < secoundStudent.getId());
    }

    @Test
    public void fixtureMaleStudent() {
        Student student = Fixture.from(Student.class).gimme("validMaleStudent");
        assertNotNull("Male Student should not be null", student);
        assertNotNull("Students id should not be null", student.getId());
    }

    @Test
    public void shouldGimmeOneMaleAndOneFemaleStudent() {
        List<Student> students = Fixture.from(Student.class).gimme(2, "validFemaleStudent", "validMaleStudent");
        assertEquals(Gender.FEMALE, students.get(0).getGender());
        assertEquals(Gender.MALE, students.get(1).getGender());
    }


    @Test
    public void shouldGeneratePropertyValueBasedOnOtherProperty() {
        Student student = Fixture.from(Student.class).gimme("valid", new Rule() {{
            add("firstName", "Nykolas");
            add("lastName", new DependentFunction() {
                @Override
                public <T> T generateValue(Set<Property> properties) {
                    Optional<Property> firstName = properties.stream().filter(property -> property.getName().equals("firstName")).findFirst();

                    if(firstName.get().getValue().equals("Nykolas")) {
                        return (T) "Lima";
                    } else {
                        return (T) "Ronaldo";
                    }
                }
            });
        }});

        assertEquals("Lima", student.getLastName());
    }

}
