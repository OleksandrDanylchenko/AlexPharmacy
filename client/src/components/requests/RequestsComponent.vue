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
          class="fadeInLeft mb-3 mr-4"
          v-wow
          data-wow-delay="0.1s"
        />
        <SecondRequest
          :manufacturersTrademarks="manufacturersTrademarks"
          @addError="addError"
          class="fadeInLeft mb-3 mr-4"
          v-wow
          data-wow-delay="0.2s"
        />
        <ThirdRequest
          :manufacturersTrademarks="manufacturersTrademarks"
          @addError="addError"
          class="fadeInLeft mb-3 mr-4"
          v-wow
          data-wow-delay="0.3s"
        />
        <FourthRequest
          :clientsFirstNames="clientsFirstNames"
          :clientsBirthdays="clientsBirthdays"
          @addError="addError"
          class="fadeInLeft mb-3 mr-4"
          v-wow
          data-wow-delay="0.4s"
        />
        <FifthRequest
          :drugsNames="drugsNames"
          @addError="addError"
          class="fadeInLeft mb-3 mr-4"
          v-wow
          data-wow-delay="0.5s"
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
        <SixthRequest
          :clientsBirthdays="clientsBirthdays"
          @addError="addError"
          class="fadeInLeft mb-3 mr-4"
          v-wow
          data-wow-delay="0.3s"
        />
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
import ThirdRequest from "./ThirdRequest";
import FourthRequest from "./FourthRequest";
import FifthRequest from "./FifthRequest";
import SixthRequest from "./SixthRequest";

export default {
  name: "RequestsComponent",
  mixins: [MessagesErrorsDismissMixin],
  components: {
    Sidebar,
    MessagesErrorsComponent,
    FirstRequest,
    SecondRequest,
    ThirdRequest,
    FourthRequest,
    FifthRequest,
    SixthRequest
  },
  data() {
    return {
      requests: [
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
      clientsBirthdays: []
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
              text: this.$moment(client.birthday).format("DD.MM.YYYY")
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
