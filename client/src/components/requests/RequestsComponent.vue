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

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.1s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-0 variant="outline-success">
              <span class="text-primary">1. {{ requests[0].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-0"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>
                Оберіть
                <abbr title="Drug name (назва препарату)">DG</abbr> препарату:
              </b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.2s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-1 variant="outline-success">
              <span class="text-primary">2. {{ requests[1].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-1"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>fdhdfhdfh</b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.3s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-2 variant="outline-success">
              <span class="text-primary">3. {{ requests[2].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-2"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>fdhdfhdfh</b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.4s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-3 variant="outline-success">
              <span class="text-primary">4. {{ requests[3].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-3"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>fdhdfhdfh</b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.5s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-4 variant="outline-success">
              <span class="text-primary">5. {{ requests[4].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-4"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>fdhdfhdfh</b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>
      </div>

      <div class="w-50">
        <h1
          class="display-4 text-center mb-3 text-primary fadeInLeft"
          v-wow
          data-wow-delay="0.2s"
        >
          Множинні запити
        </h1>

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.3s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-5 variant="outline-success">
              <span class="text-primary">1. {{ requests[5].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-5"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>fdhdfhdfh</b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.4s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-6 variant="outline-success">
              <span class="text-primary">2. {{ requests[6].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-6"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>fdhdfhdfh</b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>

        <b-card
          no-body
          class="mt-2 mx-auto w-75 fadeInLeft"
          v-wow
          data-wow-delay="0.5s"
        >
          <b-card-header header-tag="header" class="p-0" role="tab">
            <b-button block v-b-toggle.request-7 variant="outline-success">
              <span class="text-primary">3. {{ requests[7].text }}</span>
            </b-button>
          </b-card-header>
          <b-collapse
            id="request-7"
            accordion="plain-request-accordion"
            role="tabpanel"
          >
            <b-card-body>
              <b-card-text>fdhdfhdfh</b-card-text>
            </b-card-body>
          </b-collapse>
        </b-card>
      </div>
    </div>
  </div>
</template>

<script>
import Sidebar from "../common/Sidebar";
import DataService from "../../service/DataService";
import { MessagesErrorsDismissMixin } from "../../mixins/MessagesErrorsDismissMixin";
import MessagesErrorsComponent from "../common/MessagesErrorsComponent";

export default {
  name: "RequestsComponent",
  mixins: [MessagesErrorsDismissMixin],
  components: {
    Sidebar,
    MessagesErrorsComponent
  },
  data() {
    return {
      requests: [
        {
          text: "Знайти торгові марки постачальників, що постачають препарат DN"
        },
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
      drugNames: [],

      manufacturersResource: "manufacturers",
      manufacturersTrademarks: [],

      clientsResource: "clients",
      clientsFirstName: [],
      clientsBirthday: []
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
            this.drugNames.push(drugOption);
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
            this.clientsFirstName.push(firstNameOption);

            let birthdayOption = {
              value: client.birthday,
              text: client.birthday
            };
            this.clientsBirthday.push(birthdayOption);
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
