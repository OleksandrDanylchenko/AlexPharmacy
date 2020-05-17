<template>
  <div>
    <b-button v-b-toggle.request-4 variant="outline-danger" class="w-100">
      <span class="text-primary" style="font-size: 1.3em">
        4. {{ requestText }}
      </span>
    </b-button>
    <b-collapse id="request-4" class="mt-1">
      <b-card>
        <b-card-text>
          <b-form-group>
            <b-input-group prepend="Оберіть ім'я" class="mb-2">
              <b-form-select
                v-model="chosenClientFirstName"
                :options="clientsFirstNames"
                @change="handleFourthRequest"
              />
            </b-input-group>
            <b-input-group prepend="Оберіть день народження">
              <b-form-select
                v-model="chosenClientBirthday"
                :options="clientsBirthdays"
                @change="handleFourthRequest"
              />
            </b-input-group>
          </b-form-group>
          <b-table
            hover
            :items="drugs"
            :fields="fields"
            :busy.sync="isBusy"
            primary-key="id"
            class="m-0"
          >
            <template v-slot:table-busy>
              <div class="text-center text-danger mt-1">
                <b-spinner
                  type="grow"
                  class="align-middle"
                  style="width: 1.5rem; height: 1.5rem;"
                />
              </div>
            </template>
            <template v-slot:cell(drugName)="data">
              {{ data.item.name }}
            </template>
          </b-table>
        </b-card-text>
      </b-card>
    </b-collapse>
  </div>
</template>

<script>
import DataService from "../../service/DataService";

export default {
  name: "FourthRequest",
  props: ["clientsFirstNames", "clientsBirthdays"],
  data() {
    return {
      requestText:
        "Знайти назви препаратів, придбаних клієнтом з ім'ям clientFirstName та датою народження CB",
      fields: [
        {
          key: "drugName",
          label: "Назва препарату",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      chosenClientFirstName: null,
      chosenClientBirthday: null,
      drugs: []
    };
  },
  methods: {
    handleFourthRequest() {
      if (this.chosenClientFirstName && this.chosenClientBirthday) {
        this.isBusy = true;
        DataService.searchRequest(
          `drugs/search/findDrugBoughtByClientWithFirstNameAndBirthday?firstName=${this.chosenClientFirstName}&birthday=${this.chosenClientBirthday}`
        )
          .then(response => {
            this.drugs = response.data._embedded.drugs;
            this.isBusy = false;
          })
          .catch(error => {
            this.$emit("addError", `${error}`);
          });
      }
    }
  }
};
</script>
