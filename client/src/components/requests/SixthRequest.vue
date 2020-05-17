<template>
  <div>
    <b-button v-b-toggle.request-6 variant="outline-danger" class="w-100">
      <span class="text-primary" style="font-size: 1.3em">
        6. {{ requestText }}
      </span>
    </b-button>
    <b-collapse id="request-6" class="mt-1">
      <b-card>
        <b-card-text>
          <b-form-group>
            <b-input-group prepend="Оберіть CB">
              <b-form-select
                v-model="chosenBirthday"
                :options="clientsBirthdays"
                @change="handleSixRequest"
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
            <template v-slot:cell(birthday)="data">
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
  name: "SixthRequest",
  props: ["clientsBirthdays"],
  data() {
    return {
      requestText:
        "Знайти клієнтів, котрі придбали такі ж препарати, що й покупець, який народився CB",
      fields: [
        {
          key: "id",
          label: "ID",
          sortable: true,
          thClass: "text-center text-danger",
          tdClass: "text-center text-danger",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "firstName",
          label: "Ім'я",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "lastName",
          label: "Прізвище",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "birthday",
          label: "День народження",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      chosenBirthday: null,
      clients: []
    };
  },
  methods: {
    handleSixRequest() {
      this.isBusy = true;
      DataService.searchRequest(
        `clients/search/findClientByBirthdayWithSameDrugBasket?birthday=${this.chosenBirthday}`
      )
        .then(response => {
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
