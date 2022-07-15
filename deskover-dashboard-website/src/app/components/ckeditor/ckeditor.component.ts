import {Component, forwardRef, Input, OnInit} from '@angular/core';
import * as Editor from '../../build/ckeditor5';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";

@Component({
  selector: 'app-ckeditor',
  templateUrl: './ckeditor.component.html',
  styleUrls: ['./ckeditor.component.scss'],
  providers: [{
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CKEditorComponent),
      multi: true
  }]
})
export class CKEditorComponent implements OnInit, ControlValueAccessor {
  public Editor = Editor;
  public config: any;

  @Input() id: string;
  @Input() name: string;

  private _value: string = '';

  get value() {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
    this.onChange(value);
  }

  constructor() {
    this.config = {
      placeholder: 'Product description.....',
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
      mediaEmbed: {
        toolbar: [
          'mediaEmbed'
        ]
      },
      table: {
        contentToolbar: [
          'tableRow', 'tableColumn', 'mergeTableCells', '|',
          'tableProperties', 'tableCellProperties', 'toggleTableCaption', '|',
        ]
      },
      fontSize: {
        options: [10, 12, 14, 'default', 18, 20, 22],
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
          'undo', 'redo', '|',
          'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor', 'highlight', '|',
          'alignment', '|',
          'link', 'insertImage', 'blockQuote', 'insertTable', 'mediaEmbed', 'codeBlock', 'htmlEmbed','|',
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

  onChange(value: string) {
  }

  onTouch() {
  }

  onReady(editor) {
    if (editor.model.schema.isRegistered('image')) {
      editor.model.schema.extend('image', { allowAttributes: 'blockIndent' });
    }
  }

  writeValue(obj: any): void {
    this._value = obj;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

}
