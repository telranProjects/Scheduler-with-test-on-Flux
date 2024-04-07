package telran.blocker.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.blocker.dto.IpData;

@Document(collection = "blocking-data")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IpDataDoc {
	@Id
	String IP;
	@Setter
	String webService;
	@Setter
	long timestamp;

	@Override
	public int hashCode() {
		return Objects.hash(IP, webService);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IpDataDoc other = (IpDataDoc) obj;
		return Objects.equals(IP, other.IP) && Objects.equals(webService, other.webService);
	}

	public IpDataDoc(IpData ipData) {
		this.IP = ipData.IP();
		this.webService = ipData.webService();
		this.timestamp = System.currentTimeMillis();
	}

}