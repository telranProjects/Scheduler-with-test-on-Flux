package telran.blocker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import telran.blocker.model.IpDataDoc;
import telran.blocker.repo.IpDataRepo;

@Component
@RequiredArgsConstructor
public class DbTestCreation {
	final IpDataRepo docRepo;
	
	@Getter
	@SuppressWarnings("serial")
	List<IpDataDoc> ipDataDocList = new ArrayList<>(){ 
		{
			add(new IpDataDoc("151.151.151.151", "simpleService", 1));
			add(new IpDataDoc("152.152.152.152", "simpleService", 2));
			add(new IpDataDoc("153.153.153.153", "simpleService", 3));
			add(new IpDataDoc("154.154.154.154", "simpleService", 4));
			add(new IpDataDoc("155.155.155.155", "simpleService", 5));
			add(new IpDataDoc("156.156.156.156", "simpleService", 6));
			add(new IpDataDoc("157.157.157.157", "simpleService", 7));
			add(new IpDataDoc("158.158.158.158", "simpleService", 8));
			add(new IpDataDoc("159.159.159.159", "simpleService", 9));
			add(new IpDataDoc("160.160.160.160", "simpleService", 10));
		}
	};
	
	public void createDB() {		
		docRepo.deleteAll();
		List<IpDataDoc> listIpData = IntStream.range(0, ipDataDocList.size())
			    .mapToObj(i -> {
			        IpDataDoc ipDataDoc = ipDataDocList.get(i);
			        ipDataDoc.setTimestamp(1000001 + i);
			        return ipDataDoc;
			    })
			    .toList();
		docRepo.saveAll(listIpData);
	}

}