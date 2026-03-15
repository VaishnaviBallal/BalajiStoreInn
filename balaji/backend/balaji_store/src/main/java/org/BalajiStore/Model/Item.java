package org.BalajiStore.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

  @Entity
    public class Item {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String unit;

        public Item() {
        }

        public Item(String name, String unit) {
            this.name = name;
            this.unit = unit;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUnit() {
            return unit;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

}
