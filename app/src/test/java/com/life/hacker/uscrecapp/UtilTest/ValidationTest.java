package com.life.hacker.uscrecapp.UtilTest;

import static org.junit.Assert.*;

import com.life.hacker.uscrecapp.Util;

import org.junit.Test;

public class ValidationTest {

    @Test
    public void emailTestInvalidUser() {
        //Invalid user component of email
        assertFalse(Util.validEmail(""));
        assertFalse(Util.validEmail("123"));
        assertFalse(Util.validEmail("abc"));
    }

    @Test
    public void emailTestInvalidDomain() {
        //Invalid domain component of email
        assertFalse(Util.validEmail("123@"));
        assertFalse(Util.validEmail("g@2.3"));
        assertFalse(Util.validEmail("12@3c"));
        assertFalse(Util.validEmail("use@su.e"));
        assertFalse(Util.validEmail("use@use.23_"));
    }

    @Test
    public void emailTestValidEmail() {
        //Valid Email
        assertTrue(Util.validEmail("Alice@gmail.com"));
        assertTrue(Util.validEmail("bOB@mit.edu"));
        assertTrue(Util.validEmail("charlie@usa.gov"));
        assertTrue(Util.validEmail("david@usc.edu"));
    }


    @Test
    public void NetIDTestNotAllDigits() {
        assertFalse(Util.validNetID("133_3"));
        assertFalse(Util.validNetID(";sdgamr'"));
        assertFalse(Util.validNetID("asfd"));
        assertFalse(Util.validNetID(".;.aeb0jvps"));
        assertFalse(Util.validNetID("kd-v.sa=Be"));
    }

    @Test
    public void NetIDTestLengthLessThan10() {
        assertFalse(Util.validNetID(""));
        assertFalse(Util.validNetID("123"));
        assertFalse(Util.validNetID("1333"));
        assertFalse(Util.validNetID("035335"));
        assertFalse(Util.validNetID("123456"));
        assertFalse(Util.validNetID("48230109"));
        assertFalse(Util.validNetID("123456789"));
    }

    @Test
    public void NetIDTestLengthGreaterThan10() {
        assertFalse(Util.validNetID(""));
        assertFalse(Util.validNetID("123"));
        assertFalse(Util.validNetID("1333"));
        assertFalse(Util.validNetID("23632322136"));
        assertFalse(Util.validNetID("34602349786y"));
        assertFalse(Util.validNetID("awg09423654223642"));
        assertFalse(Util.validNetID("254302634009423654223642"));
    }


    @Test
    public void NetIDTestValidNetID() {
        assertTrue(Util.validNetID("1234567891"));
        assertTrue(Util.validNetID("0123456789"));
        assertTrue(Util.validNetID("6123424813"));
        assertTrue(Util.validNetID("3240350428"));
        assertTrue(Util.validNetID("5123462461"));
    }
}
