export class UrlUtils {
  // convert string to slug
  public static slugify(str: string) {
    if (str) {
      return str.toString().toLowerCase().trim()
        .replace(/[àáạảãâầấậẩẫăằắặẳẵ]/g, "a") // 'a'
        .replace(/[èéẹẻẽêềếệểễ]/g, "e") // 'e'
        .replace(/[ìíịỉĩ]/g, "i") // 'i'
        .replace(/[òóọỏõôồốộổỗơờớợởỡ]/g, "o") // 'o'
        .replace(/[ùúụủũưừứựửữ]/g, "u") // 'u'
        .replace(/[ỳýỵỷỹ]/g, "y") // 'y'
        .replace(/[đ]/g, "d") // 'd'
        .replace(/\s+/g, '-') // replace space with -
        .replace(/[^\w\-]+/g, '') // remove all non-word chars
        .replace(/\-\-+/g, '-') // replace multiple - with single -
        .replace(/^-+/, '') // trim - from start of text
        .replace(/-+$/, ''); // trim - from end of text
    }
  }

  // check if url is valid
  public static isValidUrl(url: string) {
    var pattern = new RegExp('^(https?:\\/\\/)?' + // protocol
      '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
      '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
      '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
      '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
      '(\\#[-a-z\\d_]*)?$', 'i'); // fragment locator
    return !!pattern.test(url);
  }

  public static getYoutubeId(url: string) {
    var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    var match = url.match(regExp);
    if (match && match[2].length == 11) {
      return match[2];
    } else {
      return null;
    }
  }
}
