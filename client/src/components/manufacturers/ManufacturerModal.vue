<template>
  <div>
    <b-modal
      id="manufacturerModal"
      no-close-on-backdrop
      @show="loadValues"
      @hidden="resetForm"
    >
      <template v-slot:modal-title>
        <h3>{{ actionMessage }}</h3>
      </template>

      <template v-slot:default>
        <ValidationObserver>
          <b-form
            slot-scope="{ validate }"
            @submit.prevent="validate().then(handleSubmit)"
            id="manufacturerForm"
          >
            <ValidationProvider rules="required|max:150" name="торгової марки">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Торгова марка">
                  <b-form-input
                    type="text"
                    v-model="formManufacturer.trademark"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required" name="з містом">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Місто">
                  <b-form-select
                    v-model="formManufacturer.city.id"
                    :options="availableCities"
                    :state="errors[0] ? false : valid ? true : null"
                  />
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>
          </b-form>
        </ValidationObserver>
      </template>
      <template v-slot:modal-footer="{ cancel }">
        <b-button variant="primary" @click="cancel()">
          Скасувати
        </b-button>
        <b-button type="submit" form="manufacturerForm" variant="danger">
          {{ action }}
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
import DataService from "../../service/DataService";

export default {
  props: ["processingId"],
  data() {
    return {
      formManufacturer: {
        id: null,
        trademark: null,
        city: {
          id: null
        }
      },
      manufacturersResource: "manufacturers",
      availableCities: [],
      citiesResource: "cities"
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(
          this.manufacturersResource,
          this.processingId
        )
          .then(response => {
            this.$log.debug("Loaded data of city №" + this.processingId);
            this.formManufacturer.trademark = response.data.trademark;
            this.formManufacturer.city.id = response.data.city.id;
          })
          .catch(error => {
            this.$log.debug(error);
            if (error.response.status !== 404) {
              this.$emit("addError", `${error}`);
              this.$bvModal.hide("manufacturerModal");
            }
          });
        this.formManufacturer.id = this.processingId;
      });
    },
    loadChoices() {
      DataService.retrieveAllRecords(this.citiesResource)
        .then(response => {
          this.$log.debug(
            "Loaded available cities: " + response.data._embedded.cities
          );

          response.data._embedded.cities.forEach(record => {
            let cityOption = {
              value: record.id,
              text: record.name
            };
            this.availableCities.push(cityOption);
          });

          if (this.availableCities.length === 0) {
            const citiesNotFoundError =
              "Таблиця міст не містить записів. Не можна створити виробника";
            this.$log.debug(citiesNotFoundError);
            this.$emit("addError", citiesNotFoundError);
            this.$bvModal.hide("manufacturerModal");
          }
        })
        .catch(error => {
          this.$log.debug(error);
          this.$emit("addError", error);
          this.$bvModal.hide("manufacturerModal");
        });
    },
    resetForm() {
      this.formManufacturer = {
        id: null,
        trademark: null,
        city: {
          id: null
        }
      };
    },
    handleSubmit() {
      if (this.formManufacturer.id < 0)
        this.$emit("addManufacturer", this.formManufacturer);
      else this.$emit("updateManufacturer", this.formManufacturer);
    }
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0
        ? "Додайте нового виробника"
        : "Змініть виробника";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    }
  },
  created() {
    this.loadChoices();
  }
};
</script>
