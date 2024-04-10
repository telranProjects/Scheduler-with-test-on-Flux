package telran.blocker;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import telran.blocker.dto.IpData;
import telran.blocker.model.IpDataDoc;
import telran.blocker.repo.IpDataRepo;

@SpringBootTest
@Slf4j
class ScheduledCleanerTests {
	@Autowired
	IpDataRepo dataRepo;

	@Value("${app.scheduled.cleaner.border}")
	long timeBorder;

	@Value("${app.scheduled.cleaner.delay}")
	long timeDelay;

	@SuppressWarnings("serial")
	List<IpData> ipDataList = new ArrayList<>() {
		{
			add(new IpData("131.131.131.131", "simpleService", 1));
			add(new IpData("132.132.132.132", "simpleService", 2));
			add(new IpData("133.133.133.133", "simpleService", 3));
			add(new IpData("134.134.134.134", "simpleService", 4));
			add(new IpData("135.135.135.135", "simpleService", 5));
			add(new IpData("136.136.136.136", "simpleService", 6));
			add(new IpData("137.137.137.137", "simpleService", 7));
			add(new IpData("138.138.138.138", "simpleService", 8));
			add(new IpData("139.139.139.139", "simpleService", 9));
			add(new IpData("140.140.140.140", "simpleService", 10));

			add(new IpData("141.141.141.141", "simpleService", 1));
			add(new IpData("142.142.142.142", "simpleService", 2));
			add(new IpData("143.143.143.143", "simpleService", 3));
			add(new IpData("144.144.144.144", "simpleService", 4));
			add(new IpData("145.145.145.145", "simpleService", 5));
			add(new IpData("146.146.146.146", "simpleService", 6));
			add(new IpData("147.147.147.147", "simpleService", 7));
			add(new IpData("148.148.148.148", "simpleService", 8));
			add(new IpData("149.149.149.149", "simpleService", 9));
			add(new IpData("150.150.150.150", "simpleService", 10));

			add(new IpData("151.151.151.151", "simpleService", 1));
			add(new IpData("152.152.152.152", "simpleService", 2));
			add(new IpData("153.153.153.153", "simpleService", 3));
			add(new IpData("154.154.154.154", "simpleService", 4));
			add(new IpData("155.155.155.155", "simpleService", 5));
			add(new IpData("156.156.156.156", "simpleService", 6));
			add(new IpData("157.157.157.157", "simpleService", 7));
			add(new IpData("158.158.158.158", "simpleService", 8));
			add(new IpData("159.159.159.159", "simpleService", 9));
			add(new IpData("160.160.160.160", "simpleService", 10));
		}
	};

	List<IpDataDoc> listInRepo = null;
	Integer size = null;
	Long allTime = null;

	@PostConstruct
	void setup() {
		size = ipDataList.size();
		allTime = timeBorder + timeDelay;
	}

	@Test
	void test() throws InterruptedException {

		Flux<Long> fluxInterval = Flux.interval(Duration.ofSeconds(1));
		fluxInterval.parallel().subscribe(n -> doTest(n));
		Thread.sleep(60000);

	}

	private void doTest(long n) {
		IpDataDoc ipDataDoc = getRandomIpData(size);
		dataRepo.save(ipDataDoc);

		listInRepo = dataRepo.findAll();
		long currentTimeStamp = System.currentTimeMillis();
		listInRepo.stream().forEach(l -> {
			long tS = l.getTimestamp();
			assertTrue(currentTimeStamp - tS < allTime);
			log.trace("IP:{} TS: {}; ", l.getIP(), tS);
		});
		System.out.printf("%s\n", n + 1);
	}

	private IpDataDoc getRandomIpData(int size) {
		int i = ThreadLocalRandom.current().nextInt(0, size);
		return new IpDataDoc(ipDataList.get(i));
	}

}
