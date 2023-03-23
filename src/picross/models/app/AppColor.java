package picross.models.app;

public enum AppColor {
	GREEN("#a9d18e"),
	BLUE("#8fd1d1"),
	PURPLE("#d18fd1"),
	ORANGE("#d19f8f"),
	GRAY("#f2f2f2");

	private String hex;

	AppColor(String hex) {
		this.hex = hex;
	}

	public String getHex() {
		return hex;
	}
}
