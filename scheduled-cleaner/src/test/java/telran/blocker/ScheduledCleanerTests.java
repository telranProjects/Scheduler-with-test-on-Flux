package telran.blocker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.MongoTransactionManager;

import telran.blocker.model.IpDataDoc;
import telran.blocker.repo.IpDataRepo;

@SpringBootTest
class ScheduledCleanerTests {

	@Autowired
	DbTestCreation dbCreation;
	@Autowired
	IpDataRepo dataRepo;
	@MockBean
	MongoTransactionManager transactionManager;
	
	static final long PSEUDO_CURRENT_TIME = 1100011;
	static final long CLEANER_BOREDER = 100005;
	
	List<IpDataDoc> listInRepo = null;
	
	@BeforeEach
	void setUp() {
		dbCreation.createDB();
	}	
	
	@Test
	void test() {
		final long timeToClean = PSEUDO_CURRENT_TIME - CLEANER_BOREDER;		
		List<IpDataDoc> listBeforeClean = dbCreation.getIpDataDocList();		
		List<IpDataDoc> listAfterClean = listBeforeClean.subList(5, listBeforeClean.size());		
		assertNotNull(dataRepo.findAll());

		listInRepo = dataRepo.findAll();		
	    assertAll(
	        () -> assertEquals(listBeforeClean.size(), listInRepo.size()),
	        () -> assertTrue(listInRepo.containsAll(listBeforeClean))
	    );
		
		dataRepo.deleteByTimestampLessThan(timeToClean);
		listInRepo = dataRepo.findAll();		
		assertAll(
		        () -> assertEquals(listAfterClean.size(), listInRepo.size()),
		        () -> assertTrue(listInRepo.containsAll(listAfterClean))
		    );		
	}
	
}
