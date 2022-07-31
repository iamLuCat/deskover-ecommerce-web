import Swal from 'sweetalert2';
import {Confirm, Loading, Notify} from "notiflix";

export class NotiflixUtils {
  constructor() { }

  static showLoading() {
    Loading.standard({
      backgroundColor: 'rgba(255,255,255,0.8)',
    });
  }

  static removeLoading() {
    Loading.remove();
  }

  static showConfirm(title: string, message: string, okCallback: Function, cancelCallback: Function = () => {}) {
    Confirm.show(
      title,
      message,
      'Có',
      'Không',
      () => {
        okCallback();
      },
      () => {
        cancelCallback();
      },
      {
        okButtonBackground: '#1c519d',
        titleColor: '#1c519d',
      }
    )
  }

  static successNotify(title: string) {
    Notify.success(title, {
      showOnlyTheLastOne: true,
      clickToClose: true,
    });
  }

  static failureNotify(title: string) {
    Notify.failure(title,{
      showOnlyTheLastOne: true,
      clickToClose: true,
    });
  }

  static warningNotify(title: string) {
    Notify.warning(title,{
      showOnlyTheLastOne: true,
      clickToClose: true,
    });
  }
}
