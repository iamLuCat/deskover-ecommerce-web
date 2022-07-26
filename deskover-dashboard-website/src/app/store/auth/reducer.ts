import { StorageConstants } from '@/constants/storage-constants';
import * as AuthActions from './actions';

const initialState = {
  isLoggedIn: !!localStorage.getItem(StorageConstants.TOKEN),
  token: localStorage.getItem(StorageConstants.TOKEN),
};

export function authReducer(
  state = initialState,
  action: AuthActions.LoginUser
) {
  switch (action.type) {
    case AuthActions.LOGIN_USER:
      localStorage.setItem('token', action.payload);
      return {
        ...state,
        isLoggedIn: true,
        token: action.payload
      };
    case AuthActions.LOGOUT_USER:
      break;
    case AuthActions.LOAD_USER:
      break;
    default:
      return state;
  }
}
