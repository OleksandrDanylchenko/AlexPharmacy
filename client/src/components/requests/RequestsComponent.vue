<template>
  <div class="mt-4">
    <Sidebar />

    <MessagesErrorsComponent
      :messages="messages"
      :errors="errors"
      @dismissMessages="dismissMessages"
      @dismissErrors="dismissErrors"
      class="fadeInLeft"
      v-wow
    />

    <div class="d-flex">
      <div class="w-50">
        <h1 class="display-4 text-center mb-3 text-primary fadeInLeft" v-wow>
          Прості запити
        </h1>

        <FirstRequest
          :drugsNames="drugsNames"
          @addError="addError"
          class="fadeInLeft mb-3"
          v-wow
        />
        <SecondRequest
          :manufacturersTrademarks="manufacturersTrademarks"
          @addError="addError"
          class="fadeInLeft"
          v-wow
        />
      </div>
      <div class="w-50">
        <h1
          class="display-4 text-center mb-3 text-primary fadeInLeft"
          v-wow
          data-wow-delay="0.2s"
        >
          Множинні запити
        </h1>
      </div>
    </div>
  </div>
</template>

<script>
import Sidebar from "../common/Sidebar";
import DataService from "../../service/DataService";
import { MessagesErrorsDismissMixin } from "../../mixins/MessagesErrorsDismissMixin";
import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
import FirstRequest from "./FirstRequest";
import SecondRequest from "./SecondRequest";

export default {
  name: "RequestsComponent",
  mixins: [MessagesErrorsDismissMixin],
  components: {
    Sidebar,
    MessagesErrorsComponent,
    FirstRequest,
    SecondRequest
  },
  data() {
    return {
      requests: [
        {
          text:
            "Знайти прізвища та дні народження клієнтів, " +
            "що придбали хоча б один препарат виробника з торговою маркою TM"
        },
        {
          text:
            "Знайти назви хвороб, які лікуються препаратами виробника з торговою маркою TM"
        },
        {
          text:
            "Знайти назви препаратів, придбаних клієнтом з ім'ям clientFirstName та датою народження CB"
        },
        {
          text:
            "Знайти назви міст, виробники яких не постачають препарат з назвою DN"
        },
        {
          text:
            "Знайти клієнтів, котрі придбали такі ж препарати, що й покупець, який народився CB"
        },
        {
          text:
            "Знайти дні народження покупців з іменем CFN, які придбали " +
            "принаймні усі продукти від виробника TM"
        },
        {
          text: "Знайти назви міст виробників, які постачають усі препарати"
        }
      ],

      messages: [],
      errors: [],

      drugsResource: "drugs",
      drugsNames: [],

      manufacturersResource: "manufacturers",
      manufacturersTrademarks: [],

      clientsResource: "clients",
      clientsFirstNames: [],
      chosenClientFirstName: null,
      clientsBirthdays: [],
      chosenClientBirthday: null
    };
  },
  methods: {
    loadChoices() {
      DataService.retrieveAllRecords(this.drugsResource)
        .then(response => {
          this.$log.debug("Loaded available drugs: " + response.data);

          response.data.forEach(drug => {
            let drugOption = {
              value: drug.name,
              text: drug.name
            };
            this.drugsNames.push(drugOption);
          });
        })
        .catch(error => {
          this.$log.debug(error);
          MessagesErrorsDismissMixin.addError(error);
        });

      DataService.retrieveAllRecords(this.manufacturersResource)
        .then(response => {
          this.$log.debug("Loaded available manufacturers: " + response.data);

          response.data.forEach(manufacturer => {
            let trademarkOption = {
              value: manufacturer.trademark,
              text: manufacturer.trademark
            };
            this.manufacturersTrademarks.push(trademarkOption);
          });
        })
        .catch(error => {
          this.$log.debug(error);
          MessagesErrorsDismissMixin.addError(error);
        });

      DataService.retrieveAllRecords(this.clientsResource)
        .then(response => {
          this.$log.debug("Loaded available clients: " + response.data);

          response.data._embedded.clients.forEach(client => {
            let firstNameOption = {
              value: client.firstName,
              text: client.firstName
            };
            this.clientsFirstNames.push(firstNameOption);

            let birthdayOption = {
              value: client.birthday,
              text: client.birthday
            };
            this.clientsBirthdays.push(birthdayOption);
          });
        })
        .catch(error => {
          this.$log.debug(error);
          MessagesErrorsDismissMixin.addError(error);
        });
    }
  },
  created() {
    this.loadChoices();
  }
};
</script>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
</style>
