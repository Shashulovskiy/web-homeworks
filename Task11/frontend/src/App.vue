<template>
  <div id="app">
    <Header :user="user"/>
    <Middle :posts="posts" :users="users" :user="user"/>
    <Footer/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return {
      user: null,
      posts: [],
      users: []
    }
  },
  beforeMount() {
    if (localStorage.getItem("jwt") && !this.user) {
      this.$root.$emit("onJwt", localStorage.getItem("jwt"));
    }

    axios.get("/api/1/posts").then(response => {
      this.posts = response.data;
    });

    axios.get("/api/1/users").then(response => {
      this.users = response.data;
    })
  },
  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/jwt", {
        login, password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$root.$emit("onJwt", response.data);
      }).catch(error => {
        this.$root.$emit("onEnterValidationError", error.response.data);
      });
    });

    this.$root.$on("onRegister", (login, name, password) => {
      if (login.length < 3 || login.length > 16) {
        this.$root.$emit("onRegisterValidationError", "Login should be between 3 and 16 characters long")
        return;
      } else if (name.length < 1 || name.length > 32) {
        this.$root.$emit("onRegisterValidationError", "Name should be between 1 and 32 characters long")
        return;
      } else if (!login.match(/^[a-zA-Z]+$/)) {
        this.$root.$emit("onRegisterValidationError", "Login should only contain latin letters");
        return;
      } else if (!name.match(/^[a-zA-Z]+$/)) {
        this.$root.$emit("onRegisterValidationError", "Name should only contain latin letters");
        return;
      } else if (Object.values(this.users).filter(u => u.login === login).length !== 0) {
        this.$root.$emit("onRegisterValidationError", "Login is already in use");
        return;
      } else if (password === "") {
        this.$root.$emit("onRegisterValidationError", "Password cannot be empty");
        return;
      }

      axios.post("/api/1/users", {
        login, name, password
      }).catch(error => {
        this.$root.$emit("onRegisterValidationError", error.response.data);
        // eslint-disable-next-line no-unused-vars
      }).then(_ => {
        this.$root.$emit("onEnter", login, password);
      })
    })

    this.$root.$on("onWritePost", (title, text) => {
      if (!title || title.length < 5) {
        this.$root.$emit("onWritePostValidationError", "Title is too short");
        return;
      } else if (!text || text.length < 10) {
        this.$root.$emit("onWritePostValidationError", "Text is too short");
        return;
      } else if (title.length > 100) {
        this.$root.$emit("onWritePostValidationError", "Title is too long");
        return;
      } else if (text.length > 10000) {
        this.$root.$emit("onWritePostValidationError", "Text is too long");
        return;
      }
      axios.post("/api/1/posts", {
        title, text, jwt: localStorage.getItem("jwt")
        // eslint-disable-next-line no-unused-vars
      }).then(_ => {
        axios.get("/api/1/posts").then(response => {
          this.posts = response.data;
          // eslint-disable-next-line no-unused-vars
        }).then(_ => {
          this.$root.$emit("onChangePage", 'Index');
        });
      }).catch(error => {
        this.$root.$emit("onWritePostValidationError", error.response.data);
      })
    })

    this.$root.$on("onAddComment", (text, id) => {
      if (!text) {
        this.$root.$emit("onAddCommentValidationError", "Comment is too short");
        return;
      }

      axios.post("/api/1/post/" + id + "/comments", {
        text, jwt: localStorage.getItem("jwt")
        // eslint-disable-next-line no-unused-vars
      }).then(_ => {
        axios.get("/api/1/posts").then(response => {
          this.posts = response.data;
          // eslint-disable-next-line no-unused-vars
        }).then(_ => {
          axios.get("/api/1/post/" + id).then(response => {
            this.$root.$emit("showPost", response.data);
          })
        });

      }).catch(error => {
        this.$root.$emit("onAddCommentValidationError", error.response.data);
      })
    })

    this.$root.$on("onJwt", (jwt) => {
      localStorage.setItem("jwt", jwt);

      axios.get("/api/1/users/auth", {
        params: {
          jwt
        }
      }).then(response => {
        this.user = response.data;
        this.$root.$emit("onChangePage", "Index");
      }).catch(() => this.$root.$emit("onLogout"))
    });

    this.$root.$on("onLogout", () => {
      localStorage.removeItem("jwt");
      this.user = null;
    });

  }
}
</script>

<style>
#app {

}
</style>
