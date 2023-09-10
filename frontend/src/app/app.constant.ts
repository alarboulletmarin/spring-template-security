import { environment } from '../environments/environments';

export const APP_CONSTANTS = {
  headers: {
    token: 'X-App-Session-Token',
  },
  routerLinks: {
    home: '',
    login: 'login',
  },
  endpoints: {
    login: `${environment.apiURL}/login`,
  },
};
