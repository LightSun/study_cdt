package com.heaven7.cdt.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.model.ILanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompoundStatement;


public class CDTTest {

    public static void main(String[] args) throws Exception {
        String file = "/home/heaven7/heaven7/study/github/mine/study-cpp/study_setjmp/main.cpp";
        IASTTranslationUnit u = getTranslationUnit(new File(file));
        //get whole content of cpp file
       //TODO if need
        // System.out.println("simpleDeclaration.getRawSignature():"+u.getRawSignature());

        IASTComment[]  com = u.getComments();
        for (IASTComment iastComment : com) {
			System.out.println("IASTComment:"+iastComment);
		}
        //the header files of "include"
        System.out.println("--- start print all preprocess ---");
        IASTPreprocessorStatement[] ps = u.getAllPreprocessorStatements();
        for (IASTPreprocessorStatement iastPreprocessorStatement : ps) {
			System.out.println(iastPreprocessorStatement.getRawSignature());
		}
        System.out.println("--- end print all preprocess ---");
        System.out.println();

        IASTDeclaration[] decs = u.getDeclarations();
        for ( IASTDeclaration child : decs)
		{
            //func define
			if (child instanceof IASTFunctionDefinition)
			{
                IASTFunctionDefinition func = (IASTFunctionDefinition)child;
				//get return desc, eg: static int add(int a, int b) -> 'static int'
				System.out.println(func.getDeclSpecifier().getRawSignature());
				//获得函数的声明 ,eg: int add(int a, int b) ->  add(int a, int b)
				System.out.println(func.getDeclarator().getRawSignature());
				//get the body. {}之间的内容
				System.out.println(func.getBody().getRawSignature());

                IASTNode[] nodes = func.getChildren();
                System.out.println(nodes);
                for (IASTNode node : nodes){
                    if(node instanceof CPPASTCompoundStatement){
                        CPPASTCompoundStatement cn = (CPPASTCompoundStatement) node;
                        IASTStatement[] s = cn.getStatements();
                        break;
                    }
                }
                //CPPASTCompoundStatement
                IASTStatement atr;

                IASTAttribute[] attrs = func.getBody().getAttributes();
                System.out.println(attrs);
                //输出函数的全部内容
				//System.out.println(child.getRawSignature());

				//与函数的起始位置有关
				IASTFileLocation fileLocation = func.getFileLocation();
				int startLine = fileLocation.getStartingLineNumber();
				int endLine = fileLocation.getEndingLineNumber();
				System.out.println("line count:"+ (endLine-startLine));
                System.out.println();
			}else{
                System.out.println("--- non func ---");
                System.out.println(child.getRawSignature());
                System.out.println(child.getClass().getName()); //CPPASTSimpleDeclaration
            }
		}    
	        
    }
    static IASTTranslationUnit getTranslationUnit(File source) throws Exception{
        FileContent reader = FileContent.create(
                source.getAbsolutePath(), 
                getContentFile(source).toCharArray());

        //C++用GPPLanguage解析，C用GCCLanguage解析
        return GPPLanguage.getDefault().getASTTranslationUnit(
                reader, 
                new ScannerInfo(), 
                IncludeFileContentProvider.getSavedFilesProvider(), 
                null, 
                ILanguage.OPTION_IS_SOURCE_UNIT, 
                new DefaultLogService());
               
        
//        return GCCLanguage.getDefault().getASTTranslationUnit(
//                reader, 
//                new ScannerInfo(), 
//                IncludeFileContentProvider.getSavedFilesProvider(), 
//                null, 
//                ILanguage.OPTION_IS_SOURCE_UNIT, 
//                new DefaultLogService());
    }

    static String getContentFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        String line;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)))) {
            while ((line = br.readLine()) != null)
                content.append(line).append('\n');
        }

        return content.toString();
    }
}

