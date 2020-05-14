<template>
  <div>
    <b-modal
      id="clientModal"
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
            id="clientForm"
          >
            <ValidationProvider
              rules="required|alpha_spaces|max:150"
              name="імені"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Ім'я">
                  <b-form-input
                    type="text"
                    v-model="formClient.firstName"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider
              rules="required|alpha_spaces|max:150"
              name="прізвища"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Прізвище">
                  <b-form-input
                    type="text"
                    v-model="formClient.lastName"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <!--        TODO Between validation-->
            <ValidationProvider rules="required" name="з датою дня народження">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="День народження">
                  <b-form-datepicker
                    v-model="formClient.birthday"
                    placeholder=""
                    reset-button
                    locale="uk"
                    :min="minimumDate"
                    :max="maximumDate"
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
        <b-button type="submit" form="clientForm" variant="danger">
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
  name: "ClientModal",
  data() {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

    const minDate = new Date(today);
    minDate.setFullYear(minDate.getFullYear() - 110);

    const maxDate = new Date(today);
    maxDate.setFullYear(maxDate.getFullYear() - 12);

    return {
      formClient: {
        id: null,
        firstName: null,
        lastName: null,
        birthday: null
      },
      clientsResource: "clients",
      minimumDate: minDate,
      maximumDate: maxDate
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.clientsResource, this.processingId)
          .then(response => {
            this.$log.debug("Loaded data of client №" + this.processingId);
            this.formClient.firstName = response.data.firstName;
            this.formClient.lastName = response.data.lastName;
            this.formClient.birthday = response.data.birthday;
          })
          .catch(error => {
            this.$log.debug(error);
            if (error.status === 404) {
              this.$emit("addError", `${error}`);
              this.$bvModal.hide("manufacturerModal");
            }
          });
        this.formClient.id = this.processingId;
      });
    },
    resetForm() {
      this.formClient = {
        id: null,
        firstName: null,
        lastName: null,
        birthday: null
      };
    },
    handleSubmit() {
      if (this.formClient.id < 0) this.$emit("addClient", this.formClient);
      else this.$emit("updateClient", this.formClient);
    }
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0
        ? "Додайте нового клієнта"
        : "Змініть клієнта";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    }
  }
};
</script>
