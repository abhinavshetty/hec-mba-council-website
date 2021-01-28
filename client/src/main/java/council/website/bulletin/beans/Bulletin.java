package council.website.bulletin.beans;

public class Bulletin extends BulletinConcise {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8448613224223531312L;
	
	private String bulletinDesc;
	private String bulletinPosterLoc;

	public String getBulletinDesc() {
		return bulletinDesc;
	}

	public void setBulletinDesc(String bulletinDesc) {
		this.bulletinDesc = bulletinDesc;
	}

	public String getBulletinPosterLoc() {
		return bulletinPosterLoc;
	}

	public void setBulletinPosterLoc(String bulletinPosterLoc) {
		this.bulletinPosterLoc = bulletinPosterLoc;
	}
	
	

}
