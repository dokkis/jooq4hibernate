package com.fhoster.jooq4hibernate.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  WithAndWithoutAliasHibernateDSLContextTests.class,
  WithAliasHibernateDSLContextTests.class,
  WithoutAliasHibernateDSLContextTests.class
})
public class HibernateDSLContextTestSuite {

}
