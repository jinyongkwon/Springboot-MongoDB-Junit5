package site.metacoding.mongotest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "navers") // mongodb 27017의 greendb의 navers에 매핑됨
public class Naver {
    @Id
    private String _id;
    private String company;
    private String title;
}
