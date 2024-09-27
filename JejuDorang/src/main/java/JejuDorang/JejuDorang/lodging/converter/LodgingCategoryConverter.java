package JejuDorang.JejuDorang.lodging.converter;

import JejuDorang.JejuDorang.lodging.enums.LodgingCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LodgingCategoryConverter implements AttributeConverter<LodgingCategory, String> {

	@Override
	public String convertToDatabaseColumn(LodgingCategory category) {
		if (category == null) {
			return null;
		}
		return category.getEnglishName();
	}

	@Override
	public LodgingCategory convertToEntityAttribute(String englishName) {
		if (englishName == null) {
			return null;
		}
		String normalizedEnglishName = englishName.toLowerCase();

		for (LodgingCategory category : LodgingCategory.values()) {
			if (category.getEnglishName().equalsIgnoreCase(normalizedEnglishName)) {
				return category;
			}
		}
		throw new IllegalArgumentException("Unknown value: " + englishName);
	}
}
