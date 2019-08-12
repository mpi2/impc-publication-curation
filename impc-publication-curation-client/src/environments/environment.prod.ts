// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true,
  baseUrl: 'http://wp-p3s-41.ebi.ac.uk/publications/',
  publicationsApiUrl: 'http://wp-p3s-41.ebi.ac.uk:28080/graphql',
  alleleApiUrl: 'http://wp-p3s-41.ebi.ac.uk:28080/alleles',
  authUrl: 'http://wp-p3s-41.ebi.ac.uk:28080/token/generate-token',
  harvesterUrl: 'http://wp-p3s-41.ebi.ac.uk:8000/harvest',
  submissionUrl: 'http://wp-p3s-41.ebi.ac.uk:28080/submit',
  tokenKey: 'AuthToken',
  title: 'IMPC - Publications Curation',
  confirmViaEmail: false,
  consortiumPaperMarker: true,
  showOrderID: false,
  harvestAlleles: false,
  filters: [
    {
      field: 'keywords',
      name: 'Matched keyword',
      values: ['EUCOMM', 'IMPC', 'KOMP'],
      type: 'array'
    },
    {
      field: 'provenance',
      name: 'Provenance',
      values: [
        { name: 'Consortium paper', field: 'consortiumPaper' },
        { name: 'Cites consortium paper', field: 'cites' }
      ],
      type: 'boolean'
    },
    {
      field: 'publicationYear',
      name: 'Publication year',
      values: [1999, 2019],
      type: 'range'
    }
    //{ field: 'journal', name: 'Journal', values: [], type: 'array', url: 'http://localhost:8080/journals' },
    //{ field: 'gene', name: 'Gene', values: [], type: 'array', url: 'http://localhost:8080/genes'}
  ],
  categories: [
    {
      name: 'Pending',
      status: 'pending',
      icon: 'assignment',
      color: 'warn'
    },
    {
      name: 'False positive',
      status: 'falsePositive',
      icon: 'assignment_late',
      color: 'warn'
    },
    {
      name: 'Reviewed',
      status: 'reviewed',
      icon: 'assignment_turned_in',
      color: 'accent'
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
import 'zone.js/dist/zone-error'; // Included with Angular CLI.
