package com.shirish.noteapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NoteReadWriteTest.class,
        NoteCheckUnCheckTest.class,
        NoteUnCheckCheckTest.class
})

public class NoteTestSuite {

}
