export class QueryHelper {
  publicationsQuery = (
    start = 1,
    size = 20,
    filter = {},
    orderByField = 'firstPublicationDate',
    orderByDirection = 'DESC'
  ) => `{
    publications(start: ${start}, size: ${size}, orderBy: ${orderByField}_${orderByDirection}, ${filter}){
      title
      authorString
      pmid
      pmcid
      datasource
      consortiumPaper
      orderId
      status
      correspondence {
        authors
        emails
      }
      alleles{
        acc
        gacc
        geneSymbol
        project
        alleleName
        alleleSymbol
      }
      fullTextUrlList{
        url
        documentStyle
      }
      grantsList{
        grantId
        agency
      }
      journalInfo{
        dateOfPublication
        journal{
          title
        }
      }
      fragments{
        keyword
        mentions
      }
      cites
      citations {
        pmid
        references
      }
      keyword
      firstPublicationDate
      alleleCandidates {
        acc
        gacc
        geneSymbol
        project
        alleleName
        alleleSymbol
      }
    }
  }`

  setStatusQuery = (
    pmid,
    status = 'pending',
    consortiumPaper = false,
    orderId = null,
    allelesString = ''
  ) => `
  mutation {
    updateReviewed(
    pmid: \\"${pmid}\\",
    status: \\"${status}\\",
    alleles: ${allelesString},
    consortiumPaper: ${consortiumPaper},
    orderId: \\"${orderId}\\",
    alleleCandidates: []
    ){
      title
      status
    },
  }`
}
