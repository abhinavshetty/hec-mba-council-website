package council.website.comms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import council.website.action.beans.Comms;

@Mapper
public interface CommsMapper {

	public List<Comms> getEmailToShareToAdmin();
	
	public int editEmailToShareToAdmin(@Param("comms") Comms comms);
}
