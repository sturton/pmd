/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import net.sourceforge.pmd.lang.DummyLanguageModule;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.rule.MockRule;
import net.sourceforge.pmd.lang.rule.RuleReference;
import net.sourceforge.pmd.util.ResourceLoader;

import org.junit.Assert;
import org.junit.Test;

public class RuleSetFactoryTest {
	@Test
	public void testRuleSetFileName() throws RuleSetNotFoundException {
		RuleSet rs = loadRuleSet(EMPTY_RULESET);
		assertNull("RuleSet file name not expected", rs.getFileName());

		RuleSetFactory rsf = new RuleSetFactory();
		rs = rsf.createRuleSet("net/sourceforge/pmd/TestRuleset1.xml");
		assertEquals("wrong RuleSet file name", rs.getFileName(),
				"net/sourceforge/pmd/TestRuleset1.xml");
	}

	@Test
	public void testNoRuleSetFileName() throws RuleSetNotFoundException {
		RuleSet rs = loadRuleSet(EMPTY_RULESET);
		assertNull("RuleSet file name not expected", rs.getFileName());
	}

	@Test
	public void testRefs() throws Throwable {
		RuleSetFactory rsf = new RuleSetFactory();
		RuleSet rs = rsf.createRuleSet("net/sourceforge/pmd/TestRuleset1.xml");
		assertNotNull(rs.getRuleByName("TestRuleRef"));
	}

	@Test
	public void testExtendedReferences() throws Exception {
	    InputStream in = ResourceLoader.loadResourceAsStream("net/sourceforge/pmd/rulesets/reference-ruleset.xml",
		    this.getClass().getClassLoader());
	    Assert.assertNotNull("Test ruleset not found - can't continue with test!", in);

	    RuleSetFactory rsf = new RuleSetFactory();
	    RuleSets rs = rsf.createRuleSets("net/sourceforge/pmd/rulesets/reference-ruleset.xml");
	    // added by referencing a complete ruleset (TestRuleset1.xml)
	    assertNotNull(rs.getRuleByName("MockRule1"));
	    assertNotNull(rs.getRuleByName("MockRule2"));
	    assertNotNull(rs.getRuleByName("MockRule3"));
        assertNotNull(rs.getRuleByName("TestRuleRef"));

	    // added by specific reference
	    assertNotNull(rs.getRuleByName("TestRule"));
	    // this is from TestRuleset2.xml, but not referenced
	    assertNull(rs.getRuleByName("TestRule2Ruleset2"));

	    Rule mockRule3 = rs.getRuleByName("MockRule3");
	    assertEquals("Overridden message", mockRule3.getMessage());
	    assertEquals(2, mockRule3.getPriority().getPriority());

	    Rule mockRule2 = rs.getRuleByName("MockRule2");
	    assertEquals("Just combine them!", mockRule2.getMessage());
	    // assert that MockRule2 is only once added to the ruleset, so that it really
	    // overwrites the configuration inherited from TestRuleset1.xml
	    assertEquals(1, countRule(rs, "MockRule2"));

	    Rule mockRule1 = rs.getRuleByName("MockRule1");
	    assertNotNull(mockRule1);
	    PropertyDescriptor<?> prop = mockRule1.getPropertyDescriptor("testIntProperty");
	    Object property = mockRule1.getProperty(prop);
	    assertEquals("5", String.valueOf(property));

	    // included from TestRuleset3.xml
	    assertNotNull(rs.getRuleByName("Ruleset3Rule2"));
	    // excluded from TestRuleset3.xml
	    assertNull(rs.getRuleByName("Ruleset3Rule1"));

	    // overridden to 5
	    Rule ruleset4Rule1 = rs.getRuleByName("Ruleset4Rule1");
	    assertNotNull(ruleset4Rule1);
	    assertEquals(5, ruleset4Rule1.getPriority().getPriority());
	    assertEquals(1, countRule(rs, "Ruleset4Rule1"));
	    // priority overridden for whole TestRuleset4 group
	    Rule ruleset4Rule2 = rs.getRuleByName("Ruleset4Rule2");
	    assertNotNull(ruleset4Rule2);
	    assertEquals(2, ruleset4Rule2.getPriority().getPriority());
	}

    private int countRule(RuleSets rs, String ruleName) {
        int count = 0;
	    for (Rule r : rs.getAllRules()) {
	        if (ruleName.equals(r.getName())) {
	            count++;
	        }
	    }
        return count;
    }

	@Test(expected = RuleSetNotFoundException.class)
	public void testRuleSetNotFound() throws RuleSetNotFoundException {
		RuleSetFactory rsf = new RuleSetFactory();
		rsf.createRuleSet("fooooo");
	}

	@Test
	public void testCreateEmptyRuleSet() throws RuleSetNotFoundException {
		RuleSet rs = loadRuleSet(EMPTY_RULESET);
		assertEquals("test", rs.getName());
		assertEquals(0, rs.size());
	}

	@Test
	public void testSingleRule() throws RuleSetNotFoundException {
		RuleSet rs = loadRuleSet(SINGLE_RULE);
		assertEquals(1, rs.size());
		Rule r = rs.getRules().iterator().next();
		assertEquals("MockRuleName", r.getName());
		assertEquals("net.sourceforge.pmd.lang.rule.MockRule", r.getRuleClass());
		assertEquals("avoid the mock rule", r.getMessage());
	}

	@Test
	public void testMultipleRules() throws RuleSetNotFoundException {
		RuleSet rs = loadRuleSet(MULTIPLE_RULES);
		assertEquals(2, rs.size());
		Set<String> expected = new HashSet<String>();
		expected.add("MockRuleName1");
		expected.add("MockRuleName2");
		for (Rule rule : rs.getRules()) {
			assertTrue(expected.contains(rule.getName()));
		}
	}

	@Test
	public void testSingleRuleWithPriority() throws RuleSetNotFoundException {
		assertEquals(RulePriority.MEDIUM, loadFirstRule(PRIORITY).getPriority());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testProps() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(PROPERTIES);
		assertEquals("bar", r.getProperty((PropertyDescriptor<String>) r.getPropertyDescriptor("fooString")));
		assertEquals(new Integer(3), r.getProperty((PropertyDescriptor<Integer>) r.getPropertyDescriptor("fooInt")));
		assertTrue(r.getProperty((PropertyDescriptor<Boolean>) r.getPropertyDescriptor("fooBoolean")));
		assertEquals(3.0d, r.getProperty((PropertyDescriptor<Double>) r.getPropertyDescriptor("fooDouble")), 0.05);
		assertNull(r.getPropertyDescriptor("BuggleFish"));
		assertNotSame(r.getDescription().indexOf("testdesc2"), -1);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testXPath() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(XPATH);
		PropertyDescriptor<String> xpathProperty = (PropertyDescriptor<String>) r.getPropertyDescriptor("xpath");
		assertNotNull("xpath property descriptor", xpathProperty);
		assertNotSame(r.getProperty(xpathProperty).indexOf(" //Block "), -1);
	}

	@Test
	public void testFacadesOffByDefault() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(XPATH);
		assertFalse(r.usesDFA());
	}

	@Test
	public void testDFAFlag() throws RuleSetNotFoundException {
		assertTrue(loadFirstRule(DFA).usesDFA());
	}

	@Test
	public void testExternalReferenceOverride() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(REF_OVERRIDE);
		assertEquals("TestNameOverride", r.getName());
		assertEquals("Test message override", r.getMessage());
		assertEquals("Test description override", r.getDescription());
		assertEquals("Test that both example are stored", 2, r.getExamples().size());
		assertEquals("Test example override", r.getExamples().get(1));
		assertEquals(RulePriority.MEDIUM, r.getPriority());
		PropertyDescriptor<?> test2Descriptor = r.getPropertyDescriptor("test2");
		assertNotNull("test2 descriptor", test2Descriptor);
		assertEquals("override2", r.getProperty(test2Descriptor));
		PropertyDescriptor<?> test3Descriptor = r.getPropertyDescriptor("test3");
		assertNotNull("test3 descriptor", test3Descriptor);
		assertEquals("override3", r.getProperty(test3Descriptor));
		PropertyDescriptor<?> test4Descriptor = r.getPropertyDescriptor("test4");
		assertNotNull("test3 descriptor", test4Descriptor);
		assertEquals("new property", r.getProperty(test4Descriptor));
	}

	@Test
	public void testReferenceInternalToInternal()
			throws RuleSetNotFoundException {
		RuleSet ruleSet = loadRuleSet(REF_INTERNAL_TO_INTERNAL);

		Rule rule = ruleSet.getRuleByName("MockRuleName");
		assertNotNull("Could not find Rule MockRuleName", rule);

		Rule ruleRef = ruleSet.getRuleByName("MockRuleNameRef");
		assertNotNull("Could not find Rule MockRuleNameRef", ruleRef);
	}

	@Test
	public void testReferenceInternalToInternalChain()
			throws RuleSetNotFoundException {
		RuleSet ruleSet = loadRuleSet(REF_INTERNAL_TO_INTERNAL_CHAIN);

		Rule rule = ruleSet.getRuleByName("MockRuleName");
		assertNotNull("Could not find Rule MockRuleName", rule);

		Rule ruleRef = ruleSet.getRuleByName("MockRuleNameRef");
		assertNotNull("Could not find Rule MockRuleNameRef", ruleRef);

		Rule ruleRefRef = ruleSet.getRuleByName("MockRuleNameRefRef");
		assertNotNull("Could not find Rule MockRuleNameRefRef", ruleRefRef);
	}

	@Test
	public void testReferenceInternalToExternal()
			throws RuleSetNotFoundException {
		RuleSet ruleSet = loadRuleSet(REF_INTERNAL_TO_EXTERNAL);

		Rule rule = ruleSet.getRuleByName("ExternalRefRuleName");
		assertNotNull("Could not find Rule ExternalRefRuleName", rule);

		Rule ruleRef = ruleSet.getRuleByName("ExternalRefRuleNameRef");
		assertNotNull("Could not find Rule ExternalRefRuleNameRef", ruleRef);
	}

	@Test
	public void testReferenceInternalToExternalChain()
			throws RuleSetNotFoundException {
		RuleSet ruleSet = loadRuleSet(REF_INTERNAL_TO_EXTERNAL_CHAIN);

		Rule rule = ruleSet.getRuleByName("ExternalRefRuleName");
		assertNotNull("Could not find Rule ExternalRefRuleName", rule);

		Rule ruleRef = ruleSet.getRuleByName("ExternalRefRuleNameRef");
		assertNotNull("Could not find Rule ExternalRefRuleNameRef", ruleRef);

		Rule ruleRefRef = ruleSet.getRuleByName("ExternalRefRuleNameRefRef");
		assertNotNull("Could not find Rule ExternalRefRuleNameRefRef",
				ruleRefRef);
	}

	@Test
	public void testReferencePriority() throws RuleSetNotFoundException {
		RuleSetFactory rsf = new RuleSetFactory();

		rsf.setMinimumPriority(RulePriority.LOW);
		RuleSet ruleSet = rsf
				.createRuleSet(createRuleSetReferenceId(REF_INTERNAL_TO_INTERNAL_CHAIN));
		assertEquals("Number of Rules", 3, ruleSet.getRules().size());
		assertNotNull(ruleSet.getRuleByName("MockRuleName"));
		assertNotNull(ruleSet.getRuleByName("MockRuleNameRef"));
		assertNotNull(ruleSet.getRuleByName("MockRuleNameRefRef"));

		rsf.setMinimumPriority(RulePriority.MEDIUM_HIGH);
		ruleSet = rsf
				.createRuleSet(createRuleSetReferenceId(REF_INTERNAL_TO_INTERNAL_CHAIN));
		assertEquals("Number of Rules", 2, ruleSet.getRules().size());
		assertNotNull(ruleSet.getRuleByName("MockRuleNameRef"));
		assertNotNull(ruleSet.getRuleByName("MockRuleNameRefRef"));

		rsf.setMinimumPriority(RulePriority.HIGH);
		ruleSet = rsf
				.createRuleSet(createRuleSetReferenceId(REF_INTERNAL_TO_INTERNAL_CHAIN));
		assertEquals("Number of Rules", 1, ruleSet.getRules().size());
		assertNotNull(ruleSet.getRuleByName("MockRuleNameRefRef"));

		rsf.setMinimumPriority(RulePriority.LOW);
		ruleSet = rsf
				.createRuleSet(createRuleSetReferenceId(REF_INTERNAL_TO_EXTERNAL_CHAIN));
		assertEquals("Number of Rules", 3, ruleSet.getRules().size());
		assertNotNull(ruleSet.getRuleByName("ExternalRefRuleName"));
		assertNotNull(ruleSet.getRuleByName("ExternalRefRuleNameRef"));
		assertNotNull(ruleSet.getRuleByName("ExternalRefRuleNameRefRef"));

		rsf.setMinimumPriority(RulePriority.MEDIUM_HIGH);
		ruleSet = rsf
				.createRuleSet(createRuleSetReferenceId(REF_INTERNAL_TO_EXTERNAL_CHAIN));
		assertEquals("Number of Rules", 2, ruleSet.getRules().size());
		assertNotNull(ruleSet.getRuleByName("ExternalRefRuleNameRef"));
		assertNotNull(ruleSet.getRuleByName("ExternalRefRuleNameRefRef"));

		rsf.setMinimumPriority(RulePriority.HIGH);
		ruleSet = rsf
				.createRuleSet(createRuleSetReferenceId(REF_INTERNAL_TO_EXTERNAL_CHAIN));
		assertEquals("Number of Rules", 1, ruleSet.getRules().size());
		assertNotNull(ruleSet.getRuleByName("ExternalRefRuleNameRefRef"));
	}

	@Test
	public void testOverrideMessage() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(REF_OVERRIDE_ORIGINAL_NAME);
		assertEquals("TestMessageOverride", r.getMessage());
	}

	@Test
	public void testOverrideMessageOneElem() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(REF_OVERRIDE_ORIGINAL_NAME_ONE_ELEM);
		assertEquals("TestMessageOverride", r.getMessage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectExternalRef() throws IllegalArgumentException,
			RuleSetNotFoundException {
		loadFirstRule(REF_MISPELLED_XREF);
	}

	@Test
	public void testSetPriority() throws RuleSetNotFoundException {
		RuleSetFactory rsf = new RuleSetFactory();
		rsf.setMinimumPriority(RulePriority.MEDIUM_HIGH);
		assertEquals(0, rsf
				.createRuleSet(createRuleSetReferenceId(SINGLE_RULE)).size());
		rsf.setMinimumPriority(RulePriority.MEDIUM_LOW);
		assertEquals(1, rsf
				.createRuleSet(createRuleSetReferenceId(SINGLE_RULE)).size());
	}

	@Test
	public void testLanguage() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(LANGUAGE);
		assertEquals(LanguageRegistry.getLanguage(DummyLanguageModule.NAME), r.getLanguage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectLanguage() throws RuleSetNotFoundException {
		loadFirstRule(INCORRECT_LANGUAGE);
	}

	@Test
	public void testMinimumLanugageVersion() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(MINIMUM_LANGUAGE_VERSION);
		assertEquals(LanguageRegistry.getLanguage(DummyLanguageModule.NAME).getVersion("1.4"), r.getMinimumLanguageVersion());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectMinimumLanugageVersion()
			throws RuleSetNotFoundException {
		loadFirstRule(INCORRECT_MINIMUM_LANGUAGE_VERSION);
	}

	@Test
	public void testMaximumLanugageVersion() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(MAXIMUM_LANGUAGE_VERSION);
		assertEquals(LanguageRegistry.getLanguage(DummyLanguageModule.NAME).getVersion("1.7"), r.getMaximumLanguageVersion());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectMaximumLanugageVersion()
			throws RuleSetNotFoundException {
		loadFirstRule(INCORRECT_MAXIMUM_LANGUAGE_VERSION);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvertedMinimumMaximumLanugageVersions()
			throws RuleSetNotFoundException {
		loadFirstRule(INVERTED_MINIMUM_MAXIMUM_LANGUAGE_VERSIONS);
	}

	@Test
	public void testDirectDeprecatedRule() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(DIRECT_DEPRECATED_RULE);
		assertNotNull("Direct Deprecated Rule", r);
	}

	@Test
	public void testReferenceToDeprecatedRule() throws RuleSetNotFoundException {
		Rule r = loadFirstRule(REFERENCE_TO_DEPRECATED_RULE);
		assertNotNull("Reference to Deprecated Rule", r);
		assertTrue("Rule Reference", r instanceof RuleReference);
		assertFalse("Not deprecated", r.isDeprecated());
		assertTrue("Original Rule Deprecated", ((RuleReference) r).getRule()
				.isDeprecated());
		assertEquals("Rule name", r.getName(), DEPRECATED_RULE_NAME);
	}

	@Test
	public void testRuleSetReferenceWithDeprecatedRule()
			throws RuleSetNotFoundException {
		RuleSet ruleSet = loadRuleSet(REFERENCE_TO_RULESET_WITH_DEPRECATED_RULE);
		assertNotNull("RuleSet", ruleSet);
		assertFalse("RuleSet empty", ruleSet.getRules().isEmpty());
		// No deprecated Rules should be loaded when loading an entire RuleSet
		// by reference.
		Rule r = ruleSet.getRuleByName(DEPRECATED_RULE_NAME);
		assertNull("Deprecated Rule Reference", r);
		for (Rule rule : ruleSet.getRules()) {
			assertFalse("Rule not deprecated", rule.isDeprecated());
		}
	}

	@Test
	public void testExternalReferences() throws RuleSetNotFoundException {
		RuleSet rs = loadRuleSet(EXTERNAL_REFERENCE_RULE_SET);
		assertEquals(1, rs.size());
		assertEquals(MockRule.class.getName(), rs.getRuleByName(
				"MockRule").getRuleClass());
	}

	@Test
	public void testIncludeExcludePatterns() throws RuleSetNotFoundException {
		RuleSet ruleSet = loadRuleSet(INCLUDE_EXCLUDE_RULESET);

		assertNotNull("Include patterns", ruleSet.getIncludePatterns());
		assertEquals("Include patterns size", 2, ruleSet.getIncludePatterns()
				.size());
		assertEquals("Include pattern #1", "include1", ruleSet
				.getIncludePatterns().get(0));
		assertEquals("Include pattern #2", "include2", ruleSet
				.getIncludePatterns().get(1));

		assertNotNull("Exclude patterns", ruleSet.getExcludePatterns());
		assertEquals("Exclude patterns size", 3, ruleSet.getExcludePatterns()
				.size());
		assertEquals("Exclude pattern #1", "exclude1", ruleSet
				.getExcludePatterns().get(0));
		assertEquals("Exclude pattern #2", "exclude2", ruleSet
				.getExcludePatterns().get(1));
		assertEquals("Exclude pattern #3", "exclude3", ruleSet
				.getExcludePatterns().get(2));
	}

    /**
     * Rule reference can't be resolved - ref is used instead of class and the class is old (pmd 4.3 and not pmd 5).
     * @throws Exception any error
     */
    @Test(expected = RuntimeException.class)
    public void testBug1202() throws Exception {
        RuleSetReferenceId ref = createRuleSetReferenceId("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
                "<ruleset>\n" +
                "  <rule ref=\"net.sourceforge.pmd.rules.XPathRule\">\n" + 
                "    <priority>1</priority>\n" + 
                "    <properties>\n" + 
                "      <property name=\"xpath\" value=\"//TypeDeclaration\" />\n" + 
                "      <property name=\"message\" value=\"Foo\" />\n" + 
                "    </properties>\n" + 
                "  </rule>\n" + 
                "</ruleset>\n");
        RuleSetFactory ruleSetFactory = new RuleSetFactory();
        ruleSetFactory.createRuleSet(ref);
    }

    /**
     * See https://sourceforge.net/p/pmd/bugs/1225/
     * @throws Exception any error
     */
    @Test
    public void testEmptyRuleSetFile() throws Exception {
        RuleSetReferenceId ref = createRuleSetReferenceId("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
                "\n" + 
                "<ruleset name=\"Custom ruleset\" xmlns=\"http://pmd.sourceforge.net/ruleset/2.0.0\"\n" + 
                "    xmlns:xsi=\"http:www.w3.org/2001/XMLSchema-instance\"\n" + 
                "    xsi:schemaLocation=\"http://pmd.sourceforge.net/ruleset/2.0.0 http://pmd.sourceforge.net/ruleset_2_0_0.xsd\">\n" + 
                "    <description>PMD Ruleset.</description>\n" + 
                "\n" + 
                "    <exclude-pattern>.*Test.*</exclude-pattern>\n" + 
                "\n" + 
                "</ruleset>\n");
        RuleSetFactory ruleSetFactory = new RuleSetFactory();
        RuleSet ruleset = ruleSetFactory.createRuleSet(ref);
        assertEquals(0, ruleset.getRules().size());
    }

    /**
     * See https://sourceforge.net/p/pmd/bugs/1231/
     * @throws Exception any error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWrongRuleNameReferenced() throws Exception {
        RuleSetReferenceId ref = createRuleSetReferenceId("<?xml version=\"1.0\"?>\n" + 
                "<ruleset name=\"Custom ruleset for tests\"\n" + 
                "    xmlns=\"http://pmd.sourceforge.net/ruleset/2.0.0\"\n" + 
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
                "    xsi:schemaLocation=\"http://pmd.sourceforge.net/ruleset/2.0.0 http://pmd.sourceforge.net/ruleset_2_0_0.xsd\">\n" + 
                "  <description>Custom ruleset for tests</description>\n" + 
                "  <rule ref=\"net/sourceforge/pmd/TestRuleset1.xml/ThisRuleDoesNotExist\"/>\n" + 
                "</ruleset>\n");
        RuleSetFactory ruleSetFactory = new RuleSetFactory();
        ruleSetFactory.createRuleSet(ref);
    }

    /**
     * See https://sourceforge.net/p/pmd/bugs/1231/
     * @throws Exception any error
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWrongRuleNameExcluded() throws Exception {
        RuleSetReferenceId ref = createRuleSetReferenceId("<?xml version=\"1.0\"?>\n" + 
                "<ruleset name=\"Custom ruleset for tests\"\n" + 
                "    xmlns=\"http://pmd.sourceforge.net/ruleset/2.0.0\"\n" + 
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
                "    xsi:schemaLocation=\"http://pmd.sourceforge.net/ruleset/2.0.0 http://pmd.sourceforge.net/ruleset_2_0_0.xsd\">\n" + 
                "  <description>Custom ruleset for tests</description>\n" + 
                "  <rule ref=\"net/sourceforge/pmd/TestRuleset1.xml\">\n" + 
                "    <exclude name=\"ThisRuleDoesNotExist\"/>\n" + 
                "  </rule>\n" + 
                "</ruleset>\n");
        RuleSetFactory ruleSetFactory = new RuleSetFactory();
        ruleSetFactory.createRuleSet(ref);
    }

	private static final String REF_OVERRIDE_ORIGINAL_NAME = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ " <description>testdesc</description>"
			+ PMD.EOL
			+ " <rule "
			+ PMD.EOL
			+ "  ref=\"net/sourceforge/pmd/TestRuleset1.xml/MockRule1\" message=\"TestMessageOverride\"> "
			+ PMD.EOL + " </rule>" + PMD.EOL + "</ruleset>";

	private static final String REF_MISPELLED_XREF = "<?xml version=\"1.0\"?>"
			+ PMD.EOL + "<ruleset name=\"test\">" + PMD.EOL
			+ " <description>testdesc</description>" + PMD.EOL + " <rule "
			+ PMD.EOL
			+ "  ref=\"net/sourceforge/pmd/TestRuleset1.xml/FooMockRule1\"> "
			+ PMD.EOL + " </rule>" + PMD.EOL + "</ruleset>";

	private static final String REF_OVERRIDE_ORIGINAL_NAME_ONE_ELEM = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ " <description>testdesc</description>"
			+ PMD.EOL
			+ " <rule ref=\"net/sourceforge/pmd/TestRuleset1.xml/MockRule1\" message=\"TestMessageOverride\"/> "
			+ PMD.EOL + "</ruleset>";

	private static final String REF_OVERRIDE = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ " <description>testdesc</description>"
			+ PMD.EOL
			+ " <rule "
			+ PMD.EOL
			+ "  ref=\"net/sourceforge/pmd/TestRuleset1.xml/MockRule1\" "
			+ PMD.EOL
			+ "  name=\"TestNameOverride\" "
			+ PMD.EOL
			+ "  message=\"Test message override\"> "
			+ PMD.EOL
			+ "  <description>Test description override</description>"
			+ PMD.EOL
			+ "  <example>Test example override</example>"
			+ PMD.EOL
			+ "  <priority>3</priority>"
			+ PMD.EOL
			+ "  <properties>"
			+ PMD.EOL
			+ "   <property name=\"test2\" description=\"test2\" type=\"String\" value=\"override2\"/>"
			+ PMD.EOL
			+ "   <property name=\"test3\" description=\"test3\" type=\"String\"><value>override3</value></property>"
			+ PMD.EOL
			+ "   <property name=\"test4\" description=\"test4\" type=\"String\" value=\"new property\"/>"
			+ PMD.EOL + "  </properties>" + PMD.EOL + " </rule>" + PMD.EOL
			+ "</ruleset>";

	private static final String REF_INTERNAL_TO_INTERNAL = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ " <description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">"
			+ PMD.EOL
			+ "</rule>"
			+ " <rule ref=\"MockRuleName\" name=\"MockRuleNameRef\"/> "
			+ PMD.EOL + "</ruleset>";

	private static final String REF_INTERNAL_TO_INTERNAL_CHAIN = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ " <description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">"
			+ PMD.EOL
			+ "</rule>"
			+ " <rule ref=\"MockRuleName\" name=\"MockRuleNameRef\"><priority>2</priority></rule> "
			+ PMD.EOL
			+ " <rule ref=\"MockRuleNameRef\" name=\"MockRuleNameRefRef\"><priority>1</priority></rule> "
			+ PMD.EOL + "</ruleset>";

	private static final String REF_INTERNAL_TO_EXTERNAL = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ " <description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"ExternalRefRuleName\" "
			+ PMD.EOL
			+ "ref=\"net/sourceforge/pmd/TestRuleset1.xml/MockRule1\"/>"
			+ PMD.EOL
			+ " <rule ref=\"ExternalRefRuleName\" name=\"ExternalRefRuleNameRef\"/> "
			+ PMD.EOL + "</ruleset>";

	private static final String REF_INTERNAL_TO_EXTERNAL_CHAIN = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ " <description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"ExternalRefRuleName\" "
			+ PMD.EOL
			+ "ref=\"net/sourceforge/pmd/TestRuleset2.xml/TestRule\"/>"
			+ PMD.EOL
			+ " <rule ref=\"ExternalRefRuleName\" name=\"ExternalRefRuleNameRef\"><priority>2</priority></rule> "
			+ PMD.EOL
			+ " <rule ref=\"ExternalRefRuleNameRef\" name=\"ExternalRefRuleNameRefRef\"><priority>1</priority></rule> "
			+ PMD.EOL + "</ruleset>";

	private static final String EMPTY_RULESET = "<?xml version=\"1.0\"?>"
			+ PMD.EOL + "<ruleset name=\"test\">" + PMD.EOL
			+ "<description>testdesc</description>" + PMD.EOL + "</ruleset>";

	private static final String SINGLE_RULE = "<?xml version=\"1.0\"?>"
			+ PMD.EOL + "<ruleset name=\"test\">" + PMD.EOL
			+ "<description>testdesc</description>" + PMD.EOL + "<rule "
			+ PMD.EOL + "name=\"MockRuleName\" " + PMD.EOL
			+ "message=\"avoid the mock rule\" " + PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">"
			+ "<priority>3</priority>" + PMD.EOL + "</rule></ruleset>";

	private static final String MULTIPLE_RULES = "<?xml version=\"1.0\"?>"
			+ PMD.EOL + "<ruleset name=\"test\">" + PMD.EOL
			+ "<description>testdesc</description>" + PMD.EOL
			+ "<rule name=\"MockRuleName1\" " + PMD.EOL
			+ "message=\"avoid the mock rule\" " + PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">" + PMD.EOL
			+ "</rule>" + PMD.EOL + "<rule name=\"MockRuleName2\" " + PMD.EOL
			+ "message=\"avoid the mock rule\" " + PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">" + PMD.EOL
			+ "</rule></ruleset>";

	private static final String PROPERTIES = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">"
			+ PMD.EOL
			+ "<description>testdesc2</description>"
			+ PMD.EOL
			+ "<properties>"
			+ PMD.EOL
			+ "<property name=\"fooBoolean\" description=\"test\" type=\"Boolean\" value=\"true\" />"
			+ PMD.EOL
			+ "<property name=\"fooChar\" description=\"test\" type=\"Character\" value=\"B\" />"
			+ PMD.EOL
			+ "<property name=\"fooInt\" description=\"test\" type=\"Integer\" min=\"1\" max=\"10\" value=\"3\" />"
			+ PMD.EOL
			+ "<property name=\"fooFloat\" description=\"test\" type=\"Float\" min=\"1.0\" max=\"1.0\" value=\"1.0\"  />"
			+ PMD.EOL
			+ "<property name=\"fooDouble\" description=\"test\" type=\"Double\" min=\"1.0\" max=\"9.0\" value=\"3.0\"  />"
			+ PMD.EOL
			+ "<property name=\"fooString\" description=\"test\" type=\"String\" value=\"bar\" />"
			+ PMD.EOL + "</properties>" + PMD.EOL + "</rule></ruleset>";

	private static final String XPATH = "<?xml version=\"1.0\"?>" + PMD.EOL
			+ "<ruleset name=\"test\">" + PMD.EOL
			+ "<description>testdesc</description>" + PMD.EOL
			+ "<rule name=\"MockRuleName\" " + PMD.EOL
			+ "message=\"avoid the mock rule\" " + PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">"
			+ "<priority>3</priority>" + PMD.EOL + PMD.EOL
			+ "<description>testdesc2</description>" + PMD.EOL + "<properties>"
			+ PMD.EOL
			+ "<property name=\"xpath\" description=\"test\" type=\"String\">"
			+ PMD.EOL + "<value>" + PMD.EOL + "<![CDATA[ //Block ]]>" + PMD.EOL
			+ "</value>" + PMD.EOL + "</property>" + PMD.EOL + "</properties>"
			+ PMD.EOL + "</rule></ruleset>";

	private static final String PRIORITY = "<?xml version=\"1.0\"?>" + PMD.EOL
			+ "<ruleset name=\"test\">" + PMD.EOL
			+ "<description>testdesc</description>" + PMD.EOL + "<rule "
			+ PMD.EOL + "name=\"MockRuleName\" " + PMD.EOL
			+ "message=\"avoid the mock rule\" " + PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\">"
			+ "<priority>3</priority>" + PMD.EOL + "</rule></ruleset>";

	private static final String LANGUAGE = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\" language=\"dummy\">"
			+ PMD.EOL + "</rule></ruleset>";

	private static final String INCORRECT_LANGUAGE = "<?xml version=\"1.0\"?>"
			+ PMD.EOL + "<ruleset name=\"test\">" + PMD.EOL
			+ "<description>testdesc</description>" + PMD.EOL + "<rule "
			+ PMD.EOL + "name=\"MockRuleName\" " + PMD.EOL
			+ "message=\"avoid the mock rule\" " + PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\"" + PMD.EOL
			+ " language=\"bogus\">" + PMD.EOL + "</rule></ruleset>";

	private static final String MINIMUM_LANGUAGE_VERSION = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\""
			+ PMD.EOL
			+ " language=\"dummy\""
			+ PMD.EOL
			+ " minimumLanguageVersion=\"1.4\">"
			+ PMD.EOL
			+ "</rule></ruleset>";

	private static final String INCORRECT_MINIMUM_LANGUAGE_VERSION = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\""
			+ PMD.EOL
			+ " language=\"dummy\""
			+ PMD.EOL
			+ " minimumLanguageVersion=\"bogus\">"
			+ PMD.EOL
			+ "</rule></ruleset>";

	private static final String MAXIMUM_LANGUAGE_VERSION = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\""
			+ PMD.EOL
			+ " language=\"dummy\""
			+ PMD.EOL
			+ " maximumLanguageVersion=\"1.7\">"
			+ PMD.EOL
			+ "</rule></ruleset>";

	private static final String INCORRECT_MAXIMUM_LANGUAGE_VERSION = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\""
			+ PMD.EOL
			+ " language=\"dummy\""
			+ PMD.EOL
			+ " maximumLanguageVersion=\"bogus\">"
			+ PMD.EOL
			+ "</rule></ruleset>";

	private static final String INVERTED_MINIMUM_MAXIMUM_LANGUAGE_VERSIONS = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\" "
			+ PMD.EOL
			+ "language=\"dummy\""
			+ PMD.EOL
			+ " minimumLanguageVersion=\"1.7\""
			+ PMD.EOL
			+ "maximumLanguageVersion=\"1.4\">"
			+ PMD.EOL
			+ "</rule></ruleset>";

	private static final String DIRECT_DEPRECATED_RULE = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "name=\"MockRuleName\" "
			+ PMD.EOL
			+ "message=\"avoid the mock rule\" "
			+ PMD.EOL
			+ "class=\"net.sourceforge.pmd.lang.rule.MockRule\" deprecated=\"true\">"
			+ PMD.EOL + "</rule></ruleset>";

	// Note: Update this RuleSet name to a different RuleSet with deprecated
	// Rules when the Rules are finally removed.
	private static final String DEPRECATED_RULE_RULESET_NAME = "net/sourceforge/pmd/TestRuleset1.xml";

	// Note: Update this Rule name to a different deprecated Rule when the one
	// listed here is finally removed.
	private static final String DEPRECATED_RULE_NAME = "MockRule3";

	private static final String REFERENCE_TO_DEPRECATED_RULE = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "ref=\""
			+ DEPRECATED_RULE_RULESET_NAME
			+ "/"
			+ DEPRECATED_RULE_NAME + "\">" + PMD.EOL + "</rule></ruleset>";

	private static final String REFERENCE_TO_RULESET_WITH_DEPRECATED_RULE = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule "
			+ PMD.EOL
			+ "ref=\""
			+ DEPRECATED_RULE_RULESET_NAME
			+ "\">"
			+ PMD.EOL + "</rule></ruleset>";

	private static final String DFA = "<?xml version=\"1.0\"?>" + PMD.EOL
			+ "<ruleset name=\"test\">" + PMD.EOL
			+ "<description>testdesc</description>" + PMD.EOL + "<rule "
			+ PMD.EOL + "name=\"MockRuleName\" " + PMD.EOL
			+ "message=\"avoid the mock rule\" " + PMD.EOL + "dfa=\"true\" "
			+ PMD.EOL + "class=\"net.sourceforge.pmd.lang.rule.MockRule\">"
			+ "<priority>3</priority>" + PMD.EOL + "</rule></ruleset>";

	private static final String INCLUDE_EXCLUDE_RULESET = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<include-pattern>include1</include-pattern>"
			+ PMD.EOL
			+ "<include-pattern>include2</include-pattern>"
			+ PMD.EOL
			+ "<exclude-pattern>exclude1</exclude-pattern>"
			+ PMD.EOL
			+ "<exclude-pattern>exclude2</exclude-pattern>"
			+ PMD.EOL
			+ "<exclude-pattern>exclude3</exclude-pattern>"
			+ PMD.EOL
			+ "</ruleset>";

	private static final String EXTERNAL_REFERENCE_RULE_SET = "<?xml version=\"1.0\"?>"
			+ PMD.EOL
			+ "<ruleset name=\"test\">"
			+ PMD.EOL
			+ "<description>testdesc</description>"
			+ PMD.EOL
			+ "<rule ref=\"net/sourceforge/pmd/external-reference-ruleset.xml/MockRule\"/>"
			+ PMD.EOL + "</ruleset>";

	private Rule loadFirstRule(String ruleSetXml)
			throws RuleSetNotFoundException {
		RuleSet rs = loadRuleSet(ruleSetXml);
		return rs.getRules().iterator().next();
	}

	private RuleSet loadRuleSet(String ruleSetXml)
			throws RuleSetNotFoundException {
		RuleSetFactory rsf = new RuleSetFactory();
		return rsf.createRuleSet(createRuleSetReferenceId(ruleSetXml));
	}

    private static RuleSetReferenceId createRuleSetReferenceId(final String ruleSetXml) {
        return new RuleSetReferenceId(null) {
            @Override
            public InputStream getInputStream(ClassLoader classLoader) throws RuleSetNotFoundException {
                try {
                    return new ByteArrayInputStream(ruleSetXml.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
        };
    }
}
