import UserService from '../axios/userService'

const user = JSON.parse(localStorage.getItem('user'));
const initState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };


  export const auth = {
    namespaced: true,
    state: initState,
    actions: {
      login({ commit }, user) {
        return UserService.login(user).then(
          user => {
            commit('loginSuccess', user);
            return Promise.resolve(user);
          },
          error => {
            commit('loginFailure');
            return Promise.reject(error);
          }
        );
      },
      logout({ commit }) {
        UserService.logout();
        commit('logout');
      },
    //   register({ commit }, user) {
    //     return UserService.register(user).then(
    //       response => {
    //         commit('registerSuccess');
    //         return Promise.resolve(response.data);
    //       },
    //       error => {
    //         commit('registerFailure');
    //         return Promise.reject(error);
    //       }
    //     );
    //   }
    },
    mutations: {
      loginSuccess(state, user) {
        state.status.loggedIn = true;
        state.user = user;
      },
      loginFailure(state) {
        state.status.loggedIn = false;
        state.user = null;
      },
      logout(state) {
        state.status.loggedIn = false;
        state.user = null;
      },
    //   registerSuccess(state) {
    //     state.status.loggedIn = false;
    //   },
    //   registerFailure(state) {
    //     state.status.loggedIn = false;
    //   }
    }
  };