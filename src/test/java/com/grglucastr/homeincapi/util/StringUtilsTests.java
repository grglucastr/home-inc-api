package com.grglucastr.homeincapi.util;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.Serializable;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilsTests {

    @Test
    public void testConvertObjectToStringJson(){

        final String expected = "{\"name\":\"george\",\"age\":22}";
        final Person person = new Person("george", 22);

        final String json = StringUtils.asJsonString(person);

        Assert.assertThat(expected, Matchers.is(json));
    }

    @Test(expected = Exception.class)
    public void testConvertObjectToStringJsonButThrowsException(){
        final PersonNoSerializer person = new PersonNoSerializer("george", 22);
        StringUtils.asJsonString(person);
    }

    class Person implements Serializable {
        private String name;
        private int age;

        Person(String name, int age){
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    class PersonNoSerializer {
        String name;
        int age;

        public PersonNoSerializer(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
