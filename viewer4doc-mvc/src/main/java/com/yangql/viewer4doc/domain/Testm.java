// package com.yangql.viewer4doc.domain;

// import lombok.AccessLevel;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;

// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;

// @Getter
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Entity
// public class Testm {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String name;
//     private String address;

//     @Builder
//     public Testm(String name, String address) {
//         this.name = name;
//         this.address = address;
//     }
// }