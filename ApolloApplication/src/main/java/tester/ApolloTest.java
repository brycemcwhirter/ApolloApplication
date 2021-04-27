package tester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import apollo.Enum.Tier;
import apollo.Objects.PNM;
import apollo.Objects.RushClass;


public class ApolloTest {
	static RushClass testRushClass = new RushClass();
	
	@BeforeAll
	static void populateRushClass() {
		PNM pnm1 = new PNM("Johnny Baylor", "Waco TX", "@baylor.edu", "Comp Sci", true, 21, "555-555-5555", Tier.GREEN);
		PNM pnm2 = new PNM("Toby Smith", "Portland OR", "smith@baylor.edu", "Engineering", false, 20, "555-555-5555", Tier.RED);
		PNM pnm3 = new PNM("Robert Jones", "Houston TX", "jones@baylor.edu", "MIS", true, 19, "555-555-5555", Tier.GRAY);
		testRushClass.getMembers().add(pnm1);
		testRushClass.getMembers().add(pnm2);
		testRushClass.getMembers().add(pnm3);
	}
	
	@Test
	void testFindingPNM() {
		int index = testRushClass.findPerson("Johnny Baylor");
		assertEquals(index, 0);
	}
	
	@Test
	void testFindingPNM2() {
		testRushClass.getMembers().remove(1);
		int index = testRushClass.findPerson("Toby Smith");
		assertEquals(index, -1);
	}
	
	@Test
	void testEditTier() {
		testRushClass.editTier("Robert Jones", Tier.GREEN);
		int index = testRushClass.findPerson("Robert Jones");
		assertEquals(testRushClass.getMembers().get(index).getT(), Tier.GREEN);
	}
}
