export const MessagesErrorsDismissMixin = {
    methods: {
        dismissMessages() {
            this.messages = [];
        },
        dismissErrors() {
            this.errors = [];
        },
        addError(error) {
            this.errors.push(error);
        },
        addMessage(message) {
            this.messages.push(message);
        }
    }
}