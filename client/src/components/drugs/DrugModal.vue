<template>
  <div>
    <b-modal
      id="drugModal"
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
            id="drugForm"
          >
            <ValidationProvider
              rules="required|max:150"
              name="з назвою препарату"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Назва препарату">
                  <b-form-input
                    type="text"
                    v-model="formDrug.name"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required" name="інструкції">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Інструкція">
                  <b-form-textarea
                    v-model="formDrug.instruction"
                    :state="errors[0] ? false : valid ? true : null"
                    rows="3"
                    max-rows="25"
                  ></b-form-textarea>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required" name="виробника">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Виробник">
                  <b-form-select
                    v-model="formDrug.manufacturer.id"
                    :options="availableManufacturers"
                    :state="errors[0] ? false : valid ? true : null"
                  />
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <b-input-group prepend="Хвороби">
              <b-form-group class="ml-2 mb-0">
                <b-form-checkbox-group
                  v-model="formDrug.diseases"
                  :options="availableDiseases"
                  stacked
                />
              </b-form-group>
            </b-input-group>
          </b-form>
        </ValidationObserver>
      </template>
      <template v-slot:modal-footer="{ cancel }">
        <b-button variant="primary" @click="cancel()">
          Скасувати
        </b-button>
        <b-button type="submit" form="drugForm" variant="danger">
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
      formDrug: {
        id: null,
        name: null,
        instruction: null,
        manufacturer: {
          id: null
        },
        diseases: []
      },
      drugsResource: "drugs",
      availableManufacturers: [],
      manufacturersResource: "manufacturers",
      // TODO Diseases editing upload
      availableDiseases: [],
      diseasesResource: "diseases"
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.drugsResource, this.processingId)
          .then(response => {
            this.$log.debug("Loaded data of drug №" + this.processingId);
            this.formDrug.name = response.data.name;
            this.formDrug.instruction = response.data.instruction;
            this.formDrug.manufacturer.id = response.data.manufacturer.id;
            this.formDrug.diseases = response.data.diseases;
            this.formDrug.diseases.forEach(disease => delete disease.name);
          })
          .catch(error => {
            this.$log.debug(error);
            if (error.response.status !== 404) {
              this.$emit("addError", `${error}`);
              this.$bvModal.hide("drugModal");
            }
          });
        this.formDrug.id = this.processingId;
      });
    },
    loadChoices() {
      DataService.retrieveAllRecords(this.manufacturersResource)
        .then(response => {
          this.$log.debug("Loaded available manufacturers: " + response.data);

          response.data.forEach(manufacturer => {
            let diseaseOption = {
              value: manufacturer.id,
              text: manufacturer.trademark
            };
            this.availableManufacturers.push(diseaseOption);
          });
        })
        .catch(error => {
          this.$log.debug(error);
          this.$emit("addError", error);
          this.$bvModal.hide("drugModal");
        });
      DataService.retrieveAllRecords(this.diseasesResource)
        .then(response => {
          this.$log.debug(
            "Loaded available diseases: " + response.data._embedded.diseases
          );

          response.data._embedded.diseases.forEach(disease => {
            let diseaseOption = {
              value: { id: disease.id },
              text: disease.name
            };
            this.availableDiseases.push(diseaseOption);
          });
        })
        .catch(error => {
          this.$log.debug(error);
          this.$emit("addError", error);
          this.$bvModal.hide("drugModal");
        });
    },
    resetForm() {
      this.formDrug = {
        id: null,
        name: null,
        instruction: null,
        manufacturer: {
          id: null
        },
        diseases: []
      };
    },
    handleSubmit() {
      if (this.formDrug.id < 0) this.$emit("addDrug", this.formDrug);
      else this.$emit("updateDrug", this.formDrug);
    }
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0
        ? "Додайте новий препарат"
        : "Змініть препарат";
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
