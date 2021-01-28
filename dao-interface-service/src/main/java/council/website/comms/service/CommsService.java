package council.website.comms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import council.website.action.beans.Comms;
import council.website.comms.mapper.CommsMapper;

@Service
public class CommsService {

	@Autowired
	private CommsMapper commsMapper;
	
	public List<Comms> getEmailToShareToAdmin() {
		return commsMapper.getEmailToShareToAdmin();
	}
	
	public Boolean editEmailToShareToAdmin(Comms comms) {
		commsMapper.editEmailToShareToAdmin(comms);
		return true;
	}
}
