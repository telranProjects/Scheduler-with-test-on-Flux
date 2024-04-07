package telran.blocker;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.blocker.model.IpDataDoc;
import telran.blocker.repo.IpDataRepo;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class ScheduledCleanerAppl {

	final IpDataRepo docRepo;
	
	@Value("${app.scheduled.cleaner.border}")
	long timeBorder;	
	
	public static void main(String[] args) throws InterruptedException {
		 SpringApplication.run(ScheduledCleanerAppl.class, args);

	}
	
	@Scheduled(fixedDelayString = "${app.scheduled.cleaner.delay}")
	void toClean() {		
		long timeLimit = System.currentTimeMillis() - timeBorder;
		List<IpDataDoc> deletedOlder = docRepo.deleteByTimestampLessThan(timeLimit);
		deletedOlder.stream().forEach(d -> log.debug("unactual IpData: {}", d.getIP()));		
	}

}
