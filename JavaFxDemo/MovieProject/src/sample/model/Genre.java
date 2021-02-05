package sample.model;

public class Genre {
	
	private String name;
	
	public Genre(String name) {
		this.name = name.trim();
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isSameGenreAs(String other) {
		return this.name.equals(other.trim());
	}
	
	public boolean isSameGenreAs(Genre other) {
		return this.isSameGenreAs(other.name);
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
