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
      orderIds
      emmaIds
      status
      comment
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
    orderIdsString = '',
    emmaIdsString = '',
    allelesString = '',
    comment = ''
  ) => `
  mutation {
    updateReviewed(
    pmid: \\"${pmid}\\",
    status: \\"${status}\\",
    alleles: ${allelesString},
    consortiumPaper: ${consortiumPaper},
    orderIds: ${orderIdsString},
    emmaIds: ${emmaIdsString},
    comment: \\"${comment ? comment.replace(/\"/gi, '\\\\\\\"') : ''}\\",
    alleleCandidates: []
    ){
      title
      status
    },
  }`
}
