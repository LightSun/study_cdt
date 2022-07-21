package org.eclipse.cdt.core.tests;

import junit.framework.TestCase;
import org.eclipse.core.resources.IFile;

//HEAVEN7
public  abstract class BaseTestFramework extends TestCase {

    protected String name;

    public BaseTestFramework(String name) {
        this.name = name;
    }

    public BaseTestFramework() {
    }

    protected abstract void runTest() throws Throwable;

    protected void setUp() throws Exception{

    }

    protected void tearDown() throws Exception {

    }

    public IFile importFile(String file, String src){
        //TODO
        return null;
    }

    public static void fail(String msg){
        throw new RuntimeException(msg);
    }

    public static void assertEquals(Object o1, Object o2){
        if(o1 == null){
            if(o2 != null){
                throw new RuntimeException("assertEquals failed");
            }
        }else{
            if(!o1.equals(o2)){
                throw new RuntimeException("assertEquals failed");
            }
        }
    }

    public static class Project{



    }
}
