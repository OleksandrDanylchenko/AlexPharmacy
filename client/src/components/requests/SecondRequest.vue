<template>
  <div>
    <b-button v-b-toggle.request-2 variant="outline-danger" class="w-100">
      <span class="text-primary" style="font-size: 1.3em">
        2. {{ requestText }}
      </span>
    </b-button>
    <b-collapse id="request-2" class="mt-1">
      <b-card>
        <b-card-text>
          <b-form-group>
            <b-input-group prepend="Оберіть TM">
              <b-form-select
                v-model="chosenManufacturerTrademark"
                :options="manufacturersTrademarks"
                @change="handleSecondRequest"
              />
            </b-input-group>
          </b-form-group>
          <b-table
            hover
            :items="clients"
            :fields="fields"
            :busy.sync="isBusy"
            primary-key="id"
            class="m-0"
          >
            <template v-slot:table-busy>
              <div class="text-center text-success mt-1">
                <b-spinner
                  type="grow"
                  class="align-middle"
                  style="width: 1.5rem; height: 1.5rem;"
                />
              </div>
            </template>
            <template v-slot:cell(clientLastName)="data">
              {{ data.item.lastName }}
            </template>
            <template v-slot:cell(clientBirthday)="data">
              {{ data.item.birthday | moment("DD.MM.YYYY") }}
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
  name: "SecondRequest",
  props: ["manufacturersTrademarks"],
  data() {
    return {
      requestText:
        "Знайти прізвища та дні народження клієнтів, " +
        "що придбали хоча б один препарат виробника з торговою маркою TM",
      fields: [
        {
          key: "clientLastName",
          label: "Прізвище",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "clientBirthday",
          label: "День народження",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      chosenManufacturerTrademark: null,
      clients: []
    };
  },
  methods: {
    handleSecondRequest() {
      this.isBusy = true;
      DataService.searchRequest(
        `clients/search/findLastNameAndBirthdayClientWhoBoughtDrugByManufacturer?manufacturerName=${this.chosenManufacturerTrademark}`
      )
        .then(response => {
          console.log(response.data._embedded.clients);
          this.clients = response.data._embedded.clients;
          this.isBusy = false;
        })
        .catch(error => {
          this.$emit("addError", `${error}`);
        });
    }
  }
};
</script>
