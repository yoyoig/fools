package com.yoyoig.fools;

import com.yoyoig.fools.file.RowDoc;
import com.yoyoig.fools.file.RowDocFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = {FoolsApplication.class, FoolsApplicationTests.class})
@RunWith(SpringRunner.class)
public class FoolsApplicationTests {

    @Test
    public void contextLoads() {

        List<RowDoc> rowDocs = RowDocFileUtil.readRowDoc();
        System.out.println(rowDocs);
    }

}
