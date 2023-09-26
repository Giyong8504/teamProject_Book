    package org.teamproject.entities;

    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.Id;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

        @Entity
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public class Status {

            @Id
            @GeneratedValue
            private Long id;

            @Column(nullable = false)
            private String username;

            @Column(nullable = false)
            private int age;
        }
