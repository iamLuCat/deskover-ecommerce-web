import {Component, OnInit} from '@angular/core';
import * as Editor from '../../build/ckeditor5';
// import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  selector: 'app-ckeditor',
  templateUrl: './ckeditor.component.html',
  styleUrls: ['./ckeditor.component.scss']
})
export class CKEditorComponent implements OnInit {
  public Editor = Editor;

  config: any;

  constructor() {
    this.config = {
      placeholder: 'Welcome to CKEditor 5!',
      removePlugins: [
        'Title',
        'CKBox',
        'CKFinder',
        'EasyImage',
        'RealTimeCollaborativeComments',
        'RealTimeCollaborativeTrackChanges',
        'RealTimeCollaborativeRevisionHistory',
        'PresenceList',
        'Comments',
        'TrackChanges',
        'TrackChangesData',
        'RevisionHistory',
        'Pagination',
        'WProofreader',
        'MathType'
      ],
      heading: {
        options: [
          {model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph'},
          {model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1'},
          {model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2'},
          {model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3'},
          {model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4'},
          {model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5'},
          {model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6'}
        ]
      },
      image: {
        toolbar: [
          'toggleImageCaption', 'imageTextAlternative', '|',
          'imageStyle:inline', 'imageStyle:wrapText', 'imageStyle:breakText', 'imageStyle:side', '|',
          "resizeImage"
        ]
      },
      table: {
        contentToolbar: [
          'tableRow', 'tableColumn', 'mergeTableCells', '|',
          'tableProperties', 'tableCellProperties', 'toggleTableCaption', '|',
        ]
      },
      fontSize: {
        options: [ 10, 12, 14, 'default', 18, 20, 22 ],
        supportAllValues: true
      },
      htmlSupport: {
        allow: [
          {
            name: /.*/,
            attributes: true,
            classes: true,
            styles: true
          }
        ]
      },
      htmlEmbed: {
        showPreviews: true
      },
      mention: {
        feeds: [
          {
            marker: '@',
            feed: [
              '' +
              '@Nguyễn Hoài Minh', '@Phạm Văn Hải', '@Phạm Quang Vũ'
            ],
            minimumCharacters: 1
          }
        ]
      },
      toolbar: {
        items: [
          'findAndReplace', 'selectAll', '|',
          'heading', '|',
          'bold', 'italic', 'strikethrough', 'underline', 'code', 'subscript', 'superscript', 'removeFormat', '|',
          'bulletedList', 'numberedList', 'todoList', '|',
          'outdent', 'indent', '|',
          'undo', 'redo',
          '-',
          'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor', 'highlight', '|',
          'alignment', '|',
          'link', 'insertImage', 'blockQuote', 'insertTable', 'mediaEmbed', 'codeBlock', 'htmlEmbed', '|',
          'specialCharacters', 'horizontalLine', 'pageBreak', '|',
          'textPartLanguage', '|',

          'sourceEditing'
        ],
        shouldNotGroupWhenFull: true,
      },
    }
  }

  ngOnInit(): void {
  }

  public onReady(editor) {
    editor.ui.getEditableElement().parentElement.insertBefore(
      editor.ui.view.toolbar.element,
      editor.ui.getEditableElement()
    );
  }

}
