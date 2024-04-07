package telran.blocker.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.blocker.model.IpDataDoc;

public interface IpDataRepo extends MongoRepository<IpDataDoc, String> {
	
	List<IpDataDoc> findByTimestampLessThan(long timeLimit);	

	List<IpDataDoc> deleteByTimestampLessThan(long timeLimit);

}