package council.website.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class YesNoBooleanTypeHandler extends BaseTypeHandler<Boolean> {

	@Override
	public Boolean getNullableResult(ResultSet arg0, String arg1) throws SQLException {
		return convert(arg0.getString(arg1));
	}

	@Override
	public Boolean getNullableResult(ResultSet arg0, int arg1) throws SQLException {
		return convert(arg0.getString(arg1));
	}

	@Override
	public Boolean getNullableResult(java.sql.CallableStatement arg0, int arg1) throws SQLException {
		return convert(arg0.getString(arg1));
	}

	@Override
	public void setNonNullParameter(java.sql.PreparedStatement arg0, int arg1, Boolean arg2, JdbcType arg3)
			throws SQLException {
		arg0.setString(arg1, convert(arg2));
		
	}

	private String convert(Boolean b) {
        return b ? "Y" : "N";
    }

    private Boolean convert(String s) {
        return s.equals("Y");
    }
	
}
