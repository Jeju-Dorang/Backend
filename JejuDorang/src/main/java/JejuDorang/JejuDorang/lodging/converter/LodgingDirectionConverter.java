package JejuDorang.JejuDorang.lodging.converter;

import JejuDorang.JejuDorang.lodging.enums.LodgingDirection;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LodgingDirectionConverter implements AttributeConverter<LodgingDirection, String> {

	@Override
	public String convertToDatabaseColumn(LodgingDirection direction) {
		if (direction == null) {
			return null;
		}
		return direction.getEnglishName();
	}

	@Override
	public LodgingDirection convertToEntityAttribute(String englishName) {
		if (englishName == null) {
			return null;
		}
		String normalizedEnglishName = englishName.toLowerCase();

		for (LodgingDirection direction : LodgingDirection.values()) {
			if (direction.getEnglishName().equalsIgnoreCase(normalizedEnglishName)) {
				return direction;
			}
		}
		throw new IllegalArgumentException("Unknown value: " + englishName);
	}
}
