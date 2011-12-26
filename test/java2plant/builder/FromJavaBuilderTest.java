/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package java2plant.builder;

import java.util.ArrayList;
import java.io.InputStream;
import java2plant.describer.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author arthur
 */
public class FromJavaBuilderTest {

    static FromJavaBuilder instance;
    
    public FromJavaBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new FromJavaBuilder();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of buildFromStream method, of class FromJavaBuilder.
    @Test
    public void testBuildFromStream() {
        System.out.println("buildFromStream");
        InputStream inputStream = null;
        FromJavaBuilder instance = new FromJavaBuilder();
        ContextDescriber expResult = null;
        ContextDescriber result = instance.buildFromStream(inputStream);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of splitString method, of class FromJavaBuilder.
    @Test
    public void testSplitString() {
        System.out.println("splitString");
        String str = "";
        String regex = "";
        String[] expResult = null;
        String[] result = FromJavaBuilder.splitString(str, regex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of isSeparator method, of class FromJavaBuilder.
    @Test
    public void testIsSeparator() {
        System.out.println("isSeparator");
        int c = 0;
        FromJavaBuilder instance = new FromJavaBuilder();
        boolean expResult = false;
        boolean result = instance.isSeparator(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */
    @Test
    public void testExtractDeclaration() {
        System.out.println("extractDeclaration");
        String str = "public abstract class Test extends TestClass { dfdslkfjdksfj}";
        String result = instance.extractDeclaration(str);
        assertEquals("public abstract class Test extends TestClass {", result);
    }

    /**
     * Test of getNextDecla method, of class FromJavaBuilder.
    @Test
    public void testGetNextDecla() {
        System.out.println("getNextDecla");
        InputStream is = null;
        FromJavaBuilder instance = new FromJavaBuilder();
        String expResult = "";
        String result = instance.getNextDecla(is);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    @Test
    public void testGetNext() {
        System.out.println("testNext");
        String str = "private boolean isAbstract;"+
                "private ArrayList<FieldDescriber> fields = new ArrayList();" +
                "public void setName(String name) {\n this.name = name;\n }" +
                "private int getAge() { return this.age } \n";
                
        String result = instance.getNext(str);
        assertEquals("private boolean isAbstract;", result);
        str = str.substring(result.length());
        result = instance.getNext(str);
        assertEquals("private ArrayList<FieldDescriber> fields = new ArrayList();", result);
        str = str.substring(result.length());
        result = instance.getNext(str);
        assertEquals("public void setName(String name) {\n this.name = name;\n }", result);
        str = str.substring(result.length());
        result = instance.getNext(str);
        assertEquals("private int getAge() { return this.age }", result);
        str = str.substring(result.length());
        result = instance.getNext(str);
        assertEquals("", result);
    }
    


    /**
     * Test of getNext method, of class FromJavaBuilder.
    @Test
    public void testGetNext() throws Exception {
        System.out.println("getNext");
        FromJavaBuilder instance = new FromJavaBuilder();
        String expResult = "";
        String result = instance.getNext();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of parseFile method, of class FromJavaBuilder.
    @Test
    public void testParseFile() {
        System.out.println("parseFile");
        ContextDescriber cd = null;
        FromJavaBuilder instance = new FromJavaBuilder();
        instance.parseFile(cd);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of buildClassFromString method, of class FromJavaBuilder.
    @Test
    public void testBuildClassFromString() {
        System.out.println("buildClassFromString");
        String str = "";
        FromJavaBuilder instance = new FromJavaBuilder();
        ClassDescriber expResult = null;
        ClassDescriber result = instance.buildClassFromString(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of parseClass method, of class FromJavaBuilder.
    @Test
    public void testParseClass() {
        System.out.println("parseClass");
        ClassDescriber classDescriber = null;
        FromJavaBuilder instance = new FromJavaBuilder();
        instance.parseClass(classDescriber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */
    @Test
    public void testBuildClassFromString() {
        System.out.println("buildClassFromString");
        String str = "public class Mom extends Human {"
                + "int age; \n String str = ; \n"
                + "public void setAge(int age) { \n this.age = age \n } \n"
                + "private int getAge() { return this.age } \n"
                + "}";
        FromJavaBuilder instance = new FromJavaBuilder();
        ClassDescriber result = instance.buildClassFromString(str);
        assertEquals("Mom", result.getName());
        assertEquals("public", result.getVisibility().toString());

        ArrayList<FieldDescriber> fields = result.getFields();
        assertEquals("int", fields.get(0).getType());
        assertEquals("age", fields.get(0).getName());
        assertEquals("String", fields.get(1).getType());
        assertEquals("str", fields.get(1).getName());
    }

    /**
     * Test of buildMethodFromString method, of class FromJavaBuilder.
     */
    @Test
    public void testBuildMethodFromString() {
        System.out.println("buildMethodFromString");
        String str = "public int isItGood(int myArg1, int caca) { this.name = caca; }";
        MethodDescriber expResult = null;
        MethodDescriber result = instance.buildMethodFromString(str);
        assertEquals(new Visibility("public").toString(),
                result.getVisibility().toString());
        assertEquals("int", result.getReturnType());
        assertEquals("isItGood", result.getName());
        ArrayList<ArgumentDescriber> args = result.getArgs();
        assertEquals("int", args.get(0).getType());
        assertEquals("myArg1", args.get(0).getName());
        assertEquals("int", args.get(1).getType());
        //assertEquals("Map<String, File>", args.get(1).getType());
        assertEquals("caca", args.get(1).getName());
        // TODO support for Map
    }
}
