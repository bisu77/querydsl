package study.querydsl.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {
    private String username;
    private int age;


    public MemberDto() {
    }

    @QueryProjection//Dto객체가 queryDsl에 종속적이게 됨. 잘 생각해서 사용하기
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
