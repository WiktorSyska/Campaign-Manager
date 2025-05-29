// Initialize typeahead functionality
const initTypeahead = (selector, url) => {
    $(selector).select2({
        placeholder: "Start typing...",
        allowClear: true,
        ajax: {
            url: url,
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return { q: params.term };
            },
            processResults: function (data) {
                return {
                    results: data.data.map(item => ({
                        id: item.id,
                        text: item.name || item.text
                    }))
                };
            },
            cache: true
        }
    });
};