// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  publicationsApiUrl: 'http://localhost:8080/graphql',
  alleleApiUrl: 'http://localhost:8080/alleles',
  authUrl: 'http://localhost:8080/token/generate-token',
  tokenKey: 'AuthToken',
  title: 'INFRAFRONTIER - Publications Curation',
  confirmViaEmail: true,
  consortiumPaperMarker: false,
  showOrderID: true,
  filters: [
    {
      field: 'publicationYear',
      name: 'Publication year',
      values: [1990, 2019],
      type: 'range'
    }
  ],
  categories: [
    {
      name: 'Pending',
      filter: {reviewed: false, pendingEmailConfirmation: false},
      icon: 'assignment'
    },
    {
      name: 'Waiting for email confirmation',
      filter: {pendingEmailConfirmation: true},
      icon: 'markunread_mailbox'
    },
    {
      name: 'False positive',
      filter: {falsePositive: true},
      icon: 'assignment_late'
    },
    {
      name: 'Reviewed',
      filter: {reviewed: true, falsePositive: false},
      icon: 'assignment_turned_in'
    }
  ]
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
import 'zone.js/dist/zone-error';  // Included with Angular CLI.
